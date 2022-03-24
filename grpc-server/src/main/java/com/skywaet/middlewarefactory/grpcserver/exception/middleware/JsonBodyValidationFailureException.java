package com.skywaet.middlewarefactory.grpcserver.exception.middleware;

import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BaseMiddlewareException;

public class JsonBodyValidationFailureException extends BaseMiddlewareException {
    public JsonBodyValidationFailureException(String description) {
        super("JsonBodyValidator", description);
    }
}
