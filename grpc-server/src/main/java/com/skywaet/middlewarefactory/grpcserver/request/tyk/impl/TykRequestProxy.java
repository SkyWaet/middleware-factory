package com.skywaet.middlewarefactory.grpcserver.request.tyk.impl;

import com.google.protobuf.ByteString;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import coprocess.CoprocessCommon;
import coprocess.CoprocessMiniRequestObject;
import coprocess.CoprocessObject;
import lombok.Getter;
import com.skywaet.middlewarefactory.factorycommon.model.Phase;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TykRequestProxy implements TykRequest {

    private static final Map<CoprocessCommon.HookType, Phase> hookTypePhaseMap = Map.ofEntries(
            Map.entry(CoprocessCommon.HookType.Pre, Phase.PRE),
            Map.entry(CoprocessCommon.HookType.CustomKeyCheck, Phase.AUTH),
            Map.entry(CoprocessCommon.HookType.PostKeyAuth, Phase.POST_AUTH),
            Map.entry(CoprocessCommon.HookType.Post, Phase.POST),
            Map.entry(CoprocessCommon.HookType.Response, Phase.RESPONSE),
            Map.entry(CoprocessCommon.HookType.Unknown, Phase.ANY)
    );

    @Getter
    private final CoprocessObject.Object requestObject;
    private final boolean isBodyModified;

    public TykRequestProxy(CoprocessObject.Object requestObject, boolean isBodyModified) {
        this.requestObject = requestObject;
        this.isBodyModified = isBodyModified;
    }

    public TykRequestProxy(CoprocessObject.Object requestObject) {
        this(requestObject, false);
    }

    @Override
    public String getMethod() {
        return requestObject.getRequest().getMethod();
    }

    @Override
    public String getRequestUrl() {
        return requestObject.getRequest().getUrl();
    }

    @Override
    public String getRequestUri() {
        return requestObject.getRequest().getRequestUri();
    }

    @Override
    public BaseRequest setRequestUri(String newUri) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().setRequestUri(newUri);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(requestObject.getRequest().getHeadersMap());
    }

    @Override
    public BaseRequest addHeader(String header, String value) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().putSetHeaders(header, value);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest addHeaders(Map<String, String> headers) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().putAllSetHeaders(headers);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest removeHeader(String header) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().addDeleteHeaders(header);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest removeHeaders(List<String> headers) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().addAllDeleteHeaders(headers);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(requestObject.getRequest().getParamsMap());
    }

    @Override
    public BaseRequest addParameter(String parameter, String value) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().putAddParams(parameter, value);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest addParameters(Map<String, String> parameters) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().putAllAddParams(parameters);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest removeParameter(String parameter) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().addDeleteParams(parameter);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public BaseRequest removeParameter(List<String> parameters) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().addAllDeleteParams(parameters);
        return new TykRequestProxy(requestBuilder.build());
    }

    @Override
    public String getRequestBody() {
        return requestObject.getRequest().getBody();
    }

    @Override
    public BaseRequest setRequestBody(String body) {
        CoprocessObject.Object.Builder requestBuilder = requestObject.toBuilder();
        requestBuilder.getRequestBuilder().setBody(body);
        requestBuilder.getRequestBuilder().setRawBody(ByteString.copyFrom(body, Charset.defaultCharset()));
        return new TykRequestProxy(requestBuilder.build(), true);
    }

    @Override
    public boolean isBodyModified() {
        return isBodyModified;
    }

    @Override
    public BaseRequest mergeWith(BaseRequest newValue) {
        if (newValue instanceof TykRequest) {
            CoprocessObject.Object requestObject = ((TykRequest) newValue).getRequestObject();
            CoprocessObject.Object.Builder accumulatorBuilder = this.getRequestObject().toBuilder();
            CoprocessMiniRequestObject.MiniRequestObject newValRequest = requestObject.getRequest();
            CoprocessMiniRequestObject.MiniRequestObject.Builder accumulatorMiniRequestBuilder = accumulatorBuilder.getRequestBuilder();
            accumulatorMiniRequestBuilder.putAllSetHeaders(newValRequest.getSetHeadersMap());
            accumulatorMiniRequestBuilder.addAllDeleteHeaders(requestObject.getRequest().getDeleteHeadersList());

            accumulatorMiniRequestBuilder.putAllAddParams(requestObject.getRequest().getAddParamsMap());
            accumulatorMiniRequestBuilder.addAllDeleteParams(requestObject.getRequest().getDeleteParamsList());
            accumulatorMiniRequestBuilder.putAllExtendedParams(newValRequest.getExtendedParamsMap());

            if (StringUtils.hasText(newValRequest.getBody()) && newValue.isBodyModified()) {
                accumulatorMiniRequestBuilder.setBody(newValRequest.getBody());
                accumulatorMiniRequestBuilder.setRawBody(newValRequest.getRawBody());
            }
            return new TykRequestProxy(accumulatorBuilder.build(), this.isBodyModified || newValue.isBodyModified());
        }
        throw new IllegalArgumentException("Cannot merge request objects of different type");

    }

    @Override
    public Phase getRequestPhase() {
        return hookTypePhaseMap.get(requestObject.getHookType());
    }

    @Override
    public String getApiId() {
        return requestObject.getSpecMap().get("APIID");
    }

    @Override
    public String getOrgId() {
        return requestObject.getSpecMap().get("ORGID");
    }
}
