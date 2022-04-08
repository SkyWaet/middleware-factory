package com.skywaet.middlewarefactory.restapi.exception.notfound;

import com.skywaet.middlewarefactory.restapi.exception.BaseFactoryConfigurationException;

public abstract class BaseNotFoundException extends BaseFactoryConfigurationException {
    public BaseNotFoundException(String message) {
        super(message);
    }
}
