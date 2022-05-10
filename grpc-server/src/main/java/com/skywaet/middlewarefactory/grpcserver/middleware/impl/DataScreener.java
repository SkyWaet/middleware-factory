package com.skywaet.middlewarefactory.grpcserver.middleware.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.JsonBodyValidationFailureException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("DataScreener")
public class DataScreener implements BaseMiddleware {

    private final ObjectMapper mapper = new ObjectMapper();
    private static final String WILDCARD = "*";

    @Override
    public BaseRequest process(BaseRequest input, Object additionalParams) {
        BaseRequest result = input;
//        for (String key : additionalParams.keySet()) {
//            Object currentValue = additionalParams.get(key);
//            switch (key) {
//                case "json":
//                    if (currentValue instanceof List) {
//                        result = result.mergeWith(screenJsonBody(input, ((List<String>) currentValue)));
//                        break;
//                    }
//                    throw new IllegalArgumentException("Wrong configuration data format");
//                case "header":
//                    if (currentValue instanceof List) {
//                        result = result.mergeWith(screenHeaders(input, ((List<String>) currentValue)));
//                        break;
//                    }
//                    throw new IllegalArgumentException("Wrong configuration data format");
//                case "params":
//                    if (currentValue instanceof Map) {
//                        Map<String, Object> params = (Map<String, Object>) currentValue;
//                        result = result.mergeWith(screenUrlPathAndQueryParameters(input,
//                                (List<String>) params.get("query"),
//                                (List<String>) params.get("path")));
//                        break;
//                    }
//                    throw new IllegalArgumentException("Wrong configuration data format");
//                default:
//                    break;
//            }
//        }
        return result;
    }

    private BaseRequest screenJsonBody(BaseRequest request, List<String> fieldNames) {
        try {
            ObjectNode parsedBody = (ObjectNode) mapper.readTree(request.getRequestBody());
            if (fieldNames.size() == 1 && fieldNames.get(0).equals(WILDCARD)) {
                return request.setRequestBody(WILDCARD);
            } else {
                //TODO доработать для вложенных объектов
                fieldNames.forEach(name -> {
                    JsonNode foundField = parsedBody.findValue(name);
                    if (foundField != null) {
                        parsedBody.put(name, WILDCARD);
                    }
                });
            }
            return request.setRequestBody(mapper.writeValueAsString(parsedBody));
        } catch (
                JsonProcessingException e) {
            throw new JsonBodyValidationFailureException("Error while body parsing");
        }

    }

    private BaseRequest screenHeaders(BaseRequest input, List<String> headers) {
        Map<String, String> requestHeaders = input.getHeaders();
        Map<String, String> headersToMask = requestHeaders.keySet()
                .stream().filter(header -> headers.size() == 1 && headers.get(0).equals(WILDCARD)
                        || headers.contains(header))
                .collect(Collectors.toMap(Function.identity(), val -> WILDCARD));
        return input.removeHeaders(new ArrayList<>(headersToMask.keySet())).addHeaders(headersToMask);
    }

    private BaseRequest screenUrlPathAndQueryParameters(BaseRequest input,
                                                        List<String> queryParams,
                                                        List<String> pathParams) {
        //TODO доработать
        Map<String, String> requestQueryParams = input.getParameters();
        if (queryParams.size() == 1 && queryParams.get(0).equals(WILDCARD)) {
            requestQueryParams.forEach((param, value) -> {
                input.removeParameter(param);
                input.addParameter(param, WILDCARD);
            });
        } else {
            queryParams.forEach(param -> {
                if (requestQueryParams.get(param) != null) {
                    input.removeParameter(param);
                    input.addParameter(param, WILDCARD);
                }
            });
        }
        return input;
    }
}
