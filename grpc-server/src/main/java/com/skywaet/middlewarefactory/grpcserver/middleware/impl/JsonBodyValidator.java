package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.validators.JsonValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("JsonBodyValidator")
@AllArgsConstructor
@Slf4j
public class JsonBodyValidator implements BaseMiddleware {

    private final JsonValidator validator;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {
        Object schema = additionalParams.get("schema");
        try {
            validator.validateJson(input.getRequestBody(), mapper.writeValueAsString(schema));
        } catch (JsonProcessingException e) {
            log.error("Error while processing json {}", e.getMessage());
        }
        return input;
    }


}
