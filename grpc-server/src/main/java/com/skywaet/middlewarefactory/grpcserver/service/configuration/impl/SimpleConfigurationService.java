package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import coprocess.CoprocessCommon;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleConfigurationService implements IConfigurationService {
    private static final String SIMPLE_MW_NAME = "SimpleMW";
    private static final String SIMPLE_BODY_TRANSFORMER_NAME = "SimpleBodyTransformer";

    @Override
    public List<String> getMiddlewaresForRequest(CoprocessCommon.HookType type, String method, String url) {
        return List.of(SIMPLE_MW_NAME, SIMPLE_BODY_TRANSFORMER_NAME);
    }
}
