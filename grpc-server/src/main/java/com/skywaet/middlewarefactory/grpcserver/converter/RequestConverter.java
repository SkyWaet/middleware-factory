package com.skywaet.middlewarefactory.grpcserver.converter;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

public interface RequestConverter<I, P extends BaseRequest> {

    P from(I incomingRequest);

    I to(P processedRequest);
}
