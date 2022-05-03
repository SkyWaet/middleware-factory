package com.skywaet.middlewarefactory.grpcserver.exception.factory;

import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;

public abstract class BaseFactoryNotificationException extends BaseFactoryException {

    public BaseFactoryNotificationException(String message) {
        super("Error while sending notification to factory: " + message);
    }
}
