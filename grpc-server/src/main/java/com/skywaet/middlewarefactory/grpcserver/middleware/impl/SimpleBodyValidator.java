package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.exception.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import coprocess.CoprocessObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConditionalOnProperty(name = "use-simple")
@Component("JsonBodyValidator")
public class SimpleBodyValidator implements BaseMiddleware {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CoprocessObject.Object process(CoprocessObject.Object input, Map<String, Object> additionalParams) {
        CoprocessObject.Object.Builder builder = input.toBuilder();

        try {
            JsonNode node = objectMapper.readTree(builder.getRequestBuilder().getBody());
            for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
                String key = entry.getKey();
                String value = (String) entry.getValue();
                if (!node.has(key) || !node.get(key).asText().equals(value)) {
                    throw new JsonBodyValidationFailureException("Invalid request body");
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
