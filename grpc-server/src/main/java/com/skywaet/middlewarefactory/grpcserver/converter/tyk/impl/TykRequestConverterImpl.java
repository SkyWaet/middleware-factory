package com.skywaet.middlewarefactory.grpcserver.converter.tyk.impl;

import com.skywaet.middlewarefactory.grpcserver.converter.tyk.TykRequestConverter;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.impl.TykRequestProxy;
import coprocess.CoprocessObject;
import org.springframework.stereotype.Component;

@Component
public class TykRequestConverterImpl implements TykRequestConverter {
    @Override
    public TykRequest from(CoprocessObject.Object incomingRequest) {
        return new TykRequestProxy(incomingRequest);
    }

    @Override
    public CoprocessObject.Object to(TykRequest processedRequest) {
        return processedRequest.getRequestObject();
    }
}
