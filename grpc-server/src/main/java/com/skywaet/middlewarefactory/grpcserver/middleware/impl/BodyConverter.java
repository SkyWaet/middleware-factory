package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BodyConversionException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("BodyConverter")
@Slf4j
public class BodyConverter implements BaseMiddleware {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    @Getter
    private final class BodyConverterSettings {
        private String convertTo;
    }

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {
        BodyConverterSettings settings = jsonMapper.convertValue(additionalParams, BodyConverterSettings.class);
        try {
            switch (settings.getConvertTo()) {
                case "XML":
                    return input.setRequestBody(xmlMapper.writeValueAsString(input.getRequestBody()));
                case "JSON":
                    return input.setRequestBody(jsonMapper.writeValueAsString(input.getRequestBody()));
                default:
                    log.error("Error while converting request body: Wrong configuration data. Unknown conversion format : {}",
                            settings.getConvertTo());
                    throw new BodyConversionException("Wrong configuration data. Unknown conversion format : "
                            + settings.getConvertTo());
            }
        } catch (JsonProcessingException e) {
            log.error("Error while converting request body: {}", e);
            throw new BodyConversionException("Unknown error");
        }
    }
}
