package com.skywaet.middlewarefactory.grpcserver.exception;

public class JsonBodyValidationFailureException extends BaseMiddlewareException {
    public JsonBodyValidationFailureException(String description) {
        super("JsonBodyValidator", description);
    }
}
