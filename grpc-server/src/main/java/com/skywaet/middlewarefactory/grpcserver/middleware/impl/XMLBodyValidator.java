package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.validators.XMLValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("XMLBodyValidator")
@AllArgsConstructor
@Slf4j
public class XMLBodyValidator implements BaseMiddleware {
    private final XMLValidator validator;

    @Override
    public BaseRequest process(BaseRequest input, Map<String, Object> additionalParams) {
        String rawSchema = (String) additionalParams.get("schema");
        validator.validateXML(input.getBody(), rawSchema);
        return input;
    }

}
