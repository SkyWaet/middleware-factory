package com.skywaet.middlewarefactory.grpcserver.middleware;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

import java.util.Map;

public interface BaseMiddleware {

    BaseRequest process(BaseRequest input, Map<String, Object> additionalParams);
}
