package com.skywaet.middlewarefactory.grpcserver.exception.middleware;

public abstract class BaseMiddlewareException extends RuntimeException {

    protected BaseMiddlewareException(String middlewareName, String description) {
        super(String.format("%s threw an exception: %s", middlewareName, description));
    }
}
