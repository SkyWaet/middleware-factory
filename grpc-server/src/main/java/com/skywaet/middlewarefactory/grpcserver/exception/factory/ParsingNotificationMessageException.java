package com.skywaet.middlewarefactory.grpcserver.exception.factory;

public class ParsingNotificationMessageException extends BaseFactoryNotificationException {
    public ParsingNotificationMessageException(String message) {
        super("Error while parsing notification message: " + message);
    }
}
