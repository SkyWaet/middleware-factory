package com.skywaet.middlewarefactory.restapi.exception.notfound;

public class EndpointNotFoundException extends BaseNotFoundException {
    public EndpointNotFoundException() {
        super("Endpoint with given id not found");
    }
}
