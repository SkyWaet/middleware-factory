package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

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

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {
        String schema = (String) additionalParams.get("schema");
        validator.validateJson(input.getBody(), schema);
        return input;
    }


}
