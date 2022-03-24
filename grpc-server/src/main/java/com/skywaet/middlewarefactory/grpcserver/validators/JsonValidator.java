package com.skywaet.middlewarefactory.grpcserver.validators;

public interface JsonValidator {

    void validateJson(String json, String schema);
}
