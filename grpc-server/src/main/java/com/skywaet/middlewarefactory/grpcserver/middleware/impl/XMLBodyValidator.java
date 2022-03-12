package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.exception.XMLBodyValidationException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import coprocess.CoprocessObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("XMLBodyValidator")
@Slf4j
public class XMLBodyValidator implements BaseMiddleware {
    @Override
    public CoprocessObject.Object process(CoprocessObject.Object input, Map<String, Object> additionalParams) {
        String rawSchema = (String) additionalParams.get("schema");
        if (rawSchema == null) {
            throw new IllegalArgumentException("Schema cannot be null");
        }
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new StringReader(rawSchema)));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(errorHandler);
            StreamSource xmlFile = new StreamSource(new StringReader(input.getRequest().getBody()));
            validator.validate(xmlFile);
        } catch (SAXException | IOException e) {
            log.error("Error while processing request body: {}", e.getMessage());
            throw new XMLBodyValidationException(String.format("Error while json validation: %s",
                    e.getMessage()));
        }
        List<String> errors = errorHandler.getErrorMessages();
        if (errors.size() > 0) {
            String errorMessage = errors.toString();
            log.error("Error while processing request body: {}", errorMessage);
            throw new XMLBodyValidationException(String.format("Error while json validation: %s",
                    errorMessage));
        }
        return input;
    }


    private static class XMLErrorHandler implements ErrorHandler {

        @Getter
        private final List<String> errorMessages = new ArrayList<>();

        @Override
        public void warning(SAXParseException exception) {
            log.error("Warning while processing XML body: {}", exception.getMessage());
            errorMessages.add(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) {
            log.error("Error while processing XML body: {}", exception.getMessage());
            errorMessages.add(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) {
            log.error("Fatal error while processing XML body: {}", exception.getMessage());
            errorMessages.add(exception.getMessage());
        }
    }
}
