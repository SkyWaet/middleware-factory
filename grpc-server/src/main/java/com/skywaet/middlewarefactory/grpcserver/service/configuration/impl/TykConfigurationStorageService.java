package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.querydsl.core.BooleanBuilder;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.model.QEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.converter.configuration.FactoryFormatConverter;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.repository.EndpointMiddlewareRepository;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationStorageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@ConditionalOnProperty(name = "factory.configuration-storage", havingValue = "database")
@AllArgsConstructor
public class TykConfigurationStorageService implements IConfigurationStorageService {

    private final EndpointMiddlewareRepository repository;
    private final FactoryFormatConverter factoryFormatConverter;

    @Value("${factory.available-middlewares}")
    @Getter
    private List<String> availableMiddlewareNames;

    @Override
    public List<FactoryEndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request) {
        if (request instanceof TykRequest) {
            TykRequest convertedRequest = (TykRequest) request;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.phase.eq(convertedRequest.getRequestPhase()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.uri.eq(request.getRequestUri()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.method.eq(request.getMethod()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.apiId.eq(convertedRequest.getApiId()));

            return StreamSupport.stream(repository.findAll(builder, Sort.by(Sort.Direction.ASC, "place")).spliterator(), false)
                    .map(factoryFormatConverter::fromEndpointMiddlewareBinding).collect(Collectors.toList());

        }
        throw new IllegalArgumentException("Wrong request type");
    }

    //TODO make or delete
    @Override
    public void saveConfigurationForEndpoint(FactoryEndpointDto dto) {
        throw new NotImplementedException();
    }

    //TODO make or delete
    @Override
    public void deleteConfigurationForEndpoint(FactoryEndpointShortDto dto) {
        throw new NotImplementedException();
    }
}
