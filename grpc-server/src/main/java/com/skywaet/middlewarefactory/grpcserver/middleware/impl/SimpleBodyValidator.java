package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConditionalOnProperty(name = "use-simple")
@Component("JsonBodyValidator")
public class SimpleBodyValidator implements BaseMiddleware {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {

        try {
            JsonNode node = objectMapper.readTree(input.getRequestBody());
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

        return input;
    }
}
