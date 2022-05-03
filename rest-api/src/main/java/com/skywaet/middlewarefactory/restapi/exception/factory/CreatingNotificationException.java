package com.skywaet.middlewarefactory.restapi.exception.factory;

public class CreatingNotificationException extends BaseFactoryNotificationException {
    public CreatingNotificationException(String message) {
        super("Error while creating notification: " + message);
    }
}
