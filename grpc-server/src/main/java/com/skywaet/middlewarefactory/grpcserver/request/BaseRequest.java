package com.skywaet.middlewarefactory.grpcserver.request;

import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMethod;

import java.util.List;
import java.util.Map;

public interface BaseRequest {
    String getMethod();

    String getRequestUrl();

    String getRequestUri();

    BaseRequest setRequestUri(String newUri);

    Map<String, String> getHeaders();

    BaseRequest addHeader(String header, String value);

    BaseRequest addHeaders(Map<String, String> headers);

    BaseRequest removeHeader(String header);

    BaseRequest removeHeaders(List<String> headers);

    Map<String, String> getParameters();

    BaseRequest addParameter(String parameter, String value);

    BaseRequest addParameters(Map<String, String> parameters);

    BaseRequest removeParameter(String parameter);

    BaseRequest removeParameter(List<String> parameters);

    String getBody();

    BaseRequest setBody(String body);

    boolean isBodyModified();

    BaseRequest mergeWith(BaseRequest newValue);

}
