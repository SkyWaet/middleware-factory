package com.skywaet.middlewarefactory.grpcserver.service.factory;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

public interface MiddlewareFactory {

    BaseRequest processRequest(BaseRequest request);
}
