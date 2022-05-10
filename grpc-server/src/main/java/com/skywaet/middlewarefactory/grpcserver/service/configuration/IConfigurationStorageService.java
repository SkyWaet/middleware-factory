package com.skywaet.middlewarefactory.grpcserver.service.configuration;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;

import java.util.List;
import java.util.Set;

public interface IConfigurationStorageService {

    Set<FactoryEndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request);

    List<String> getAvailableMiddlewareNames();

    void saveConfigurationForEndpoint(FactoryEndpointDto dto);

    void deleteConfigurationForEndpoint(FactoryEndpointShortDto dto);

}
