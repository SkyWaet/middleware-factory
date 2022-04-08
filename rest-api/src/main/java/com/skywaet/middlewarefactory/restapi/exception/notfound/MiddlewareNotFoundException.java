package com.skywaet.middlewarefactory.restapi.exception.notfound;

public class MiddlewareNotFoundException extends BaseNotFoundException{
    public MiddlewareNotFoundException() {
        super("Middleware with given id not found");
    }
}
