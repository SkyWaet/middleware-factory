package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import org.springframework.stereotype.Component;

@Component("SimpleAddHeaders")
public class SimpleMiddleware implements BaseMiddleware {
    private static final String TEST_HEADER = "testHeader";
    private static final String TEST_HEADER_VALUE = "testVal";

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        return input.addHeader(TEST_HEADER, TEST_HEADER_VALUE);
    }
}
