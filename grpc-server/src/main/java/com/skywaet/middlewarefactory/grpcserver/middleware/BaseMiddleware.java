package com.skywaet.middlewarefactory.grpcserver.middleware;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

public interface BaseMiddleware {

    BaseRequest process(BaseRequest input, Object additionalParams);
}
