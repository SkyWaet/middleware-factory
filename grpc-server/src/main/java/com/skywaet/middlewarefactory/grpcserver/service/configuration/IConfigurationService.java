package com.skywaet.middlewarefactory.grpcserver.service.configuration;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;

import java.util.List;

public interface IConfigurationService {

    List<EndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request);

    List<String> getAllMiddlewareNames();

}
