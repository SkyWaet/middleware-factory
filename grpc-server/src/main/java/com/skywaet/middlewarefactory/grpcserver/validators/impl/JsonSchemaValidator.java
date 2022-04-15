package com.skywaet.middlewarefactory.grpcserver.validators.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersionDetector;
import com.networknt.schema.ValidationMessage;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.validators.JsonValidator;
import liquibase.pro.packaged.B;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JsonSchemaValidator implements JsonValidator {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void validateJson(String json, String schema) {
        if (json == null) {
            throw new IllegalArgumentException("Input json cannot be null");
        }
        if (schema == null) {
            throw new IllegalArgumentException("Schema cannot be null");
        }
        try {
            JsonNode parsedBody = getJsonNode(json);
            JsonSchema parsedSchema = getJsonSchema(getJsonNode(schema));
            Set<ValidationMessage> errors = parsedSchema.validate(parsedBody);
            if (errors.size() > 0) {
                String errorMessage = generateMessage(errors);
                log.error("Error while json validation: {}", errorMessage);
                throw new JsonBodyValidationFailureException(
                        String.format("Error while json validation: %s", errorMessage));
            }
        } catch (JsonProcessingException e) {
            log.error("Error while parsing json: {}", e.getMessage());
          throw new JsonBodyValidationFailureException(
                    String.format("Error while json validation: %s", e.getMessage()));
        }
    }

    private JsonNode getJsonNode(String input) throws JsonProcessingException {
        return mapper.readTree(input);
    }

    private JsonSchema getJsonSchema(JsonNode schema) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(schema));
        return factory.getSchema(schema);
    }

    private String generateMessage(Set<ValidationMessage> errors) {
        return errors.stream().limit(5).collect(Collectors.toList()).toString();
    }
}
