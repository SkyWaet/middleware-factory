package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.validators.JsonValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("JsonBodyValidator")
@AllArgsConstructor
@Slf4j
public class JsonBodyValidator implements BaseMiddleware {

    private final JsonValidator validator;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        if (additionalParams instanceof JsonSchema) {
            validator.validateJson(input.getRequestBody(), (JsonSchema) additionalParams);

            return input;
        }
        throw new BaseFactoryException("Wrong configuration format");

    }


}
