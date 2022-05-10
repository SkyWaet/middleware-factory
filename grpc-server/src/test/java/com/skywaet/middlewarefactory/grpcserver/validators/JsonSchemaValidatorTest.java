package com.skywaet.middlewarefactory.grpcserver.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.skywaet.middlewarefactory.grpcserver.converter.middleware.MiddlewareConfigurationPropertiesConverter;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.validators.impl.JsonSchemaValidator;
import coprocess.CoprocessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

class JsonSchemaValidatorTest {
    private final JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String jsonSchemaRaw = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonSchema.json"))).stream().reduce(String::concat).get();
    private final String validJson = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonValid.json"))).stream().reduce(String::concat).get();
    private final String invalidJson = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonInvalid.json"))).stream().reduce(String::concat).get();
    private final MiddlewareConfigurationPropertiesConverter propertiesConverter = new MiddlewareConfigurationPropertiesConverter();
    private JsonSchema jsonSchema;

    @BeforeEach
    void init() throws JsonProcessingException {
        Map<String, Object> config = objectMapper.readerForMapOf(Object.class).readValue(jsonSchemaRaw);
        jsonSchema = propertiesConverter.parseJsonSchemaValidatorSettings(Map.of("schema", config));
    }

    @Test
    void validateValidJsonShouldOk() {
        Assertions.assertDoesNotThrow(() -> jsonSchemaValidator.validateJson(validJson, jsonSchema));
    }

    @Test
    void validateInvalidJsonShouldThrowException() {
        Assertions.assertThrows(JsonBodyValidationFailureException.class, () -> jsonSchemaValidator.validateJson(invalidJson, jsonSchema));
    }


}