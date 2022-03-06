package com.skywaet.middlewarefactory.grpcserver.middleware;

import coprocess.CoprocessObject;

import java.util.Map;

public interface BaseMiddleware {

    CoprocessObject.Object process(CoprocessObject.Object input, Map<String, Object> additionalParams);
}
