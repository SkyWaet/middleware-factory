package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.querydsl.core.BooleanBuilder;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.QEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.repository.EndpointMiddlewareRepository;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class TykConfigurationService implements IConfigurationService {

    private final EndpointMiddlewareRepository repository;

    @Override
    public List<EndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request) {
        if (request instanceof TykRequest) {
            TykRequest convertedRequest = (TykRequest) request;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.phase.eq(convertedRequest.getRequestPhase()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.uri.eq(request.getRequestUri()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.method.eq(request.getMethod()));
            builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint.apiId.eq(Long.valueOf(convertedRequest.getApiId())));


            return StreamSupport.stream(repository.findAll(builder, Sort.by(Sort.Direction.ASC, "place")).spliterator(), false).collect(Collectors.toList());

        }
        throw new IllegalArgumentException("Wrong request type");
    }

    @Override
    public List<String> getAllMiddlewareNames() {
        return repository.getNamesOfAllUsedMiddlewares();
    }
}
