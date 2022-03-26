package com.skywaet.middlewarefactory.grpcserver.exception.middleware;

public class BodyConversionException extends BaseMiddlewareException {
    public BodyConversionException(String description) {
        super("BodyConverter", description);
    }
}
