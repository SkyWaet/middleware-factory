package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import coprocess.CoprocessObject;
import coprocess.CoprocessReturnOverrides;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("SimpleBodyValidator")
public class SimpleBodyValidator implements BaseMiddleware {

     private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CoprocessObject.Object process(CoprocessObject.Object input, Map<String, String> additionalParams) {
        CoprocessObject.Object.Builder builder = input.toBuilder();

        try {
            JsonNode node = objectMapper.readTree(builder.getRequestBuilder().getBody());
            for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!node.has(key) || !node.get(key).asText().equals(value)) {
                    CoprocessReturnOverrides.ReturnOverrides error = CoprocessReturnOverrides.ReturnOverrides.newBuilder().
                            setResponseCode(400).setResponseError("Invalid request body").build();
                    builder.getRequestBuilder().setReturnOverrides(error);
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
