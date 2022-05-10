package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BaseMiddlewareException;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BodyConversionException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component("BodyConverter")
@Slf4j
public class BodyConverter implements BaseMiddleware {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();


    @Data
    private static final class BodyConverterSettings {
        @JsonProperty("convert_to")
        private String convertTo;
    }

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        BodyConverterSettings settings = jsonMapper.convertValue(additionalParams, BodyConverterSettings.class);

        switch (settings.getConvertTo()) {
            case "XML":
                return jsonToXml(input);
            case "JSON":
                return xmlToJson(input);
            default:
                log.error("Error while converting request body: Wrong configuration data. Unknown conversion format : {}",
                        settings.getConvertTo());
                throw new BodyConversionException("Wrong configuration data. Unknown conversion format : "
                        + settings.getConvertTo());
        }
    }


    private BaseRequest jsonToXml(BaseRequest input) throws BodyConversionException {
        try {
            JsonNode jsonNode = jsonMapper.readTree(input.getRequestBody());
            return input.setRequestBody(xmlMapper.writer().withRootName("root").writeValueAsString(jsonNode));
        } catch (JsonProcessingException e) {
            log.error("Error while converting request body: {}", e);
            try {
                log.debug("Trying to parse body as XML");
                xmlToJson(input);
                log.debug("Body is already in XML format");
                return input;
            } catch (BaseMiddlewareException ex) {
                throw new BodyConversionException("Unknown error");
            }
        }
    }

    private BaseRequest xmlToJson(BaseRequest input) throws BodyConversionException {
        try {
            JsonNode xmlData = xmlMapper.readTree(input.getRequestBody().getBytes(StandardCharsets.UTF_8));
            return input.setRequestBody(jsonMapper.writeValueAsString(xmlData));
        } catch (IOException e) {
            log.error("Error while converting request body: {}", e);
            try {
                log.debug("Trying to parse body as JSON");
                jsonToXml(input);
                log.debug("Body is already in JSON format");
                return input;
            } catch (BaseMiddlewareException ex) {
                throw new BodyConversionException("Unknown error");
            }
        }

    }

    @PostConstruct
    private void configureXmlMapper() {
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
    }
}
