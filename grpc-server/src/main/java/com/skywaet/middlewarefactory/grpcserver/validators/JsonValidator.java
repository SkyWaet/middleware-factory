package com.skywaet.middlewarefactory.grpcserver.validators;

import com.networknt.schema.JsonSchema;

public interface JsonValidator {

    void validateJson(String json, JsonSchema schema);
}
