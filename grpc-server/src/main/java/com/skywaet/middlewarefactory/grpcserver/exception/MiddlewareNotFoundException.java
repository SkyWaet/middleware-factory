package com.skywaet.middlewarefactory.grpcserver.exception;

public class MiddlewareNotFoundException extends BaseFactoryException {
    public MiddlewareNotFoundException(String msg) {
        super(String.format("Middleware %s not found", msg));
    }
}
