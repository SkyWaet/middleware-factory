package com.skywaet.middlewarefactory.restapi.exception;

public abstract class BaseFactoryConfigurationException extends RuntimeException {

    public BaseFactoryConfigurationException (String message){
        super(message);
    }
}
