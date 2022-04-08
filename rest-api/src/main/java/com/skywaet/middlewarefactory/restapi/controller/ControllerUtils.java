package com.skywaet.middlewarefactory.restapi.controller;

public final class ControllerUtils {
    private ControllerUtils() {
    }

    public static final String ENDPOINT_CONTROLLER = "/endpoints";
    public static final String ID_PATH_PARAMETER = "/{id}";
    public static final String ENDPOINT_BY_ID = ENDPOINT_CONTROLLER + ID_PATH_PARAMETER;
    public static final String MIDDLEWARE_CONTROLLER = "/middlewares";
}
