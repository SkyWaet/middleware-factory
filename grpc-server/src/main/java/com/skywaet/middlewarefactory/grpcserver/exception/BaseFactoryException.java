package com.skywaet.middlewarefactory.grpcserver.exception;

public class BaseFactoryException extends RuntimeException {
    public BaseFactoryException() {
        super("Unknown factory error");
    }
}
