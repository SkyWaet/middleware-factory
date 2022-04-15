package com.skywaet.middlewarefactory.grpcserver.exception.notfound;

import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;

public class EndpointNotFoundException extends BaseFactoryException {
    public EndpointNotFoundException(String msg) {
        super(String.format("Endpoint %s not found", msg));
    }
}
