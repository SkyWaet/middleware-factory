package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import coprocess.CoprocessObject;
import org.springframework.stereotype.Component;

@Component("SimpleMW")
public class SimpleMiddleware implements BaseMiddleware {
    private static final String TEST_HEADER = "testHeader";
    private static final String TEST_HEADER_VALUE = "testVal";

    @Override
    public CoprocessObject.Object process(CoprocessObject.Object input) {
        CoprocessObject.Object.Builder builder = input.toBuilder();
        builder.getRequestBuilder().putSetHeaders(TEST_HEADER, TEST_HEADER_VALUE);
        return builder.build();
    }
}
