package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("TransformHeaders")
public class TransformHeaders implements BaseMiddleware {

    @Data
    private static final class TransformHeadersData {
        @JsonProperty("remove_headers")
        private List<String> removeHeaders = new ArrayList<>();
        @JsonProperty("add_headers")
        private Map<String, String> addHeaders = new HashMap<>();
        @JsonProperty("override_headers")
        private Map<String, String> overrideHeaders = new HashMap<>();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        TransformHeadersData data = objectMapper.convertValue(additionalParams, TransformHeadersData.class);
        return input.removeHeaders(data.getRemoveHeaders()).addHeaders(data.getAddHeaders())
                .addHeaders(data.getOverrideHeaders());
    }
}
