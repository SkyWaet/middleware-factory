package com.skywaet.middlewarefactory.grpcserver.exception.middleware;

import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BaseMiddlewareException;

public class XMLBodyValidationException extends BaseMiddlewareException {
    public XMLBodyValidationException(String description) {
        super("XMLBodyValidator", description);
    }
}
