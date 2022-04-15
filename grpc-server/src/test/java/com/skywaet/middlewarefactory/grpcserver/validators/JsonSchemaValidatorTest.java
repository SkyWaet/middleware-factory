package com.skywaet.middlewarefactory.grpcserver.validators;

import com.skywaet.middlewarefactory.grpcserver.exception.middleware.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.validators.impl.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;

import java.util.Objects;

class JsonSchemaValidatorTest {
    private final JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();

    private final String jsonSchema = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonSchema.json"))).stream().reduce(String::concat).get();
    private final String validJson = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonValid.json"))).stream().reduce(String::concat).get();
    private final String invalidJson = IOUtil.readLines(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("testJsonInvalid.json"))).stream().reduce(String::concat).get();

    @Test
    void validateValidJsonShouldOk() {
        Assertions.assertDoesNotThrow(() -> jsonSchemaValidator.validateJson(validJson, jsonSchema));
    }

    @Test
    void validateInvalidJsonShouldThrowException() {
        Assertions.assertThrows(JsonBodyValidationFailureException.class, () -> jsonSchemaValidator.validateJson(invalidJson, jsonSchema));
    }
}