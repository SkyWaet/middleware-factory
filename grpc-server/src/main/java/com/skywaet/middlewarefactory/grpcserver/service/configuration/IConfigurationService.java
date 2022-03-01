package com.skywaet.middlewarefactory.grpcserver.service.configuration;

import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import coprocess.CoprocessObject;

import java.util.List;

public interface IConfigurationService {

    List<EndpointMiddlewareBinding> getMiddlewaresForRequest(CoprocessObject.Object object);

    List<String> getAllMiddlewareNames();

}
