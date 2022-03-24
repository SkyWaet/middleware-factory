package com.skywaet.middlewarefactory.grpcserver.service.configuration;

import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

import java.util.List;

public interface IConfigurationService {

    List<EndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request);

    List<String> getAllMiddlewareNames();

}
