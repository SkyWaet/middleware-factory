package com.skywaet.middlewarefactory.grpcserver.exception;

public class XMLBodyValidationException extends BaseMiddlewareException {
    public XMLBodyValidationException(String description) {
        super("XMLBodyValidator", description);
    }
}
