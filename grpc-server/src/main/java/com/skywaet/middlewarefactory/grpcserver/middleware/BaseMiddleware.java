package com.skywaet.middlewarefactory.grpcserver.middleware;

import coprocess.CoprocessObject;

public interface BaseMiddleware {

    CoprocessObject.Object process(CoprocessObject.Object input);
}
