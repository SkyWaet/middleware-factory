package com.skywaet.middlewarefactory.grpcserver.exception;

public abstract class BaseMiddlewareException extends RuntimeException {

    protected BaseMiddlewareException(String middlewareName, String description) {
        super(String.format("%s threw an exception: %s", middlewareName, description));
    }
}
