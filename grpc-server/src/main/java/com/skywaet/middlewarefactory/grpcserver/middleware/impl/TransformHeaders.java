package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("TransformHeaders")
public class TransformHeaders implements BaseMiddleware {

    @Getter
    private final class TransformHeadersData {
        private List<String> removeHeaders;
        private Map<String, String> addHeaders;
        private Map<String, String> overrideHeaders;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {
        TransformHeadersData data = objectMapper.convertValue(additionalParams, TransformHeadersData.class);
        return input.removeHeaders(data.getRemoveHeaders()).addHeaders(data.getAddHeaders())
                .addHeaders(data.getOverrideHeaders());
    }
}
