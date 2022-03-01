package com.skywaet.middlewarefactory.grpcserver.util;

import coprocess.CoprocessMiniRequestObject.MiniRequestObject;
import coprocess.CoprocessObject;
import org.springframework.stereotype.Component;


@Component
public final class MergeObjectsUtils {

    public CoprocessObject.Object mergeObjects(CoprocessObject.Object accumulator, CoprocessObject.Object newValue) {
        CoprocessObject.Object.Builder accumulatorBuilder = accumulator.toBuilder();
        MiniRequestObject newValRequest = newValue.getRequest();
        MiniRequestObject.Builder accumulatorMiniRequestBuilder = accumulatorBuilder.getRequestBuilder();
        accumulatorMiniRequestBuilder.putAllSetHeaders(newValRequest.getSetHeadersMap());
        accumulatorMiniRequestBuilder.addAllDeleteHeaders(newValue.getRequest().getDeleteHeadersList());

        accumulatorMiniRequestBuilder.putAllAddParams(newValue.getRequest().getAddParamsMap());
        accumulatorMiniRequestBuilder.addAllDeleteParams(newValue.getRequest().getDeleteParamsList());
        accumulatorMiniRequestBuilder.putAllExtendedParams(newValRequest.getExtendedParamsMap());

        if (accumulatorMiniRequestBuilder.getBody() == null || newValRequest.getBody() != null) {
            accumulatorMiniRequestBuilder.setBody(newValRequest.getBody());
        }
        if (newValRequest.getReturnOverrides() != null) {
            accumulatorMiniRequestBuilder.setReturnOverrides(newValRequest.getReturnOverrides());
        }
        return accumulatorBuilder.build();
    }

}
