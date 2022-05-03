package com.skywaet.middlewarefactory.restapi.exception.factory;

import com.skywaet.middlewarefactory.restapi.exception.BaseFactoryConfigurationException;

public abstract class BaseFactoryNotificationException extends BaseFactoryConfigurationException {

    public BaseFactoryNotificationException(String message) {
        super("Error while sending notification to factory: " + message);
    }
}
