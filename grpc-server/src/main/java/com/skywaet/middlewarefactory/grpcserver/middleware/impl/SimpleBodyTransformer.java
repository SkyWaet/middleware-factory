package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import coprocess.CoprocessObject;
import org.springframework.stereotype.Component;

@Component("SimpleBodyTransformer")
public class SimpleBodyTransformer implements BaseMiddleware {

    private static final String TEST_JSON = "{\"test\":\"data\"}";

    @Override
    public CoprocessObject.Object process(CoprocessObject.Object input) {
        CoprocessObject.Object.Builder builder = input.toBuilder();
        builder.getRequestBuilder().setBody(TEST_JSON);
        return builder.build();
    }
}
