package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import lombok.AllArgsConstructor;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "use-simple")
@AllArgsConstructor
public class SimpleConfigurationService implements IConfigurationService {
    private static final String SIMPLE_MW_NAME = "SimpleMW";
    private static final String SIMPLE_BODY_TRANSFORMER_NAME = "SimpleBodyTransformer";

    @Override
    public List<EndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request) {
        //return List.of(SIMPLE_MW_NAME, SIMPLE_BODY_TRANSFORMER_NAME);
        return null;
    }

    @Override
    public List<String> getAllMiddlewareNames() {
        return List.of(SIMPLE_MW_NAME);
    }
}
