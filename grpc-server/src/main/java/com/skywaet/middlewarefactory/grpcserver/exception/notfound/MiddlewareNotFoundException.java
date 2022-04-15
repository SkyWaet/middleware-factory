package com.skywaet.middlewarefactory.grpcserver.exception.notfound;

import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;

public class MiddlewareNotFoundException extends BaseFactoryException {
    public MiddlewareNotFoundException(String msg) {
        super(String.format("Middleware %s not found", msg));
    }
}
