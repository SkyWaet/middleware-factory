package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.validators.XMLValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("XMLBodyValidator")
@AllArgsConstructor
@Slf4j
public class XMLBodyValidator implements BaseMiddleware {
    private final XMLValidator validator;

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        String rawSchema = "";
        validator.validateXML(input.getRequestBody(), rawSchema);
        return input;
    }

}
