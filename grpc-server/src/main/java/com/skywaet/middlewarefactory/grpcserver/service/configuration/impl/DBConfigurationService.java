package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.querydsl.core.BooleanBuilder;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.LocalHookType;
import com.skywaet.middlewarefactory.grpcserver.model.QEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.repository.EndpointMiddlewareRepository;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import coprocess.CoprocessObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class DBConfigurationService implements IConfigurationService {

    private final EndpointMiddlewareRepository repository;

    @Override
    public List<EndpointMiddlewareBinding> getMiddlewaresForRequest(CoprocessObject.Object request) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.middleware.type
                .eq(LocalHookType.getLocalHook(request.getHookType())));
        builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint
                .uri.eq(request.getRequest().getRequestUri()));
        builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint
                .method.eq(request.getRequest().getMethod()));
        builder.and(QEndpointMiddlewareBinding.endpointMiddlewareBinding.endpoint
                .apiId.eq(Long.valueOf(request.getSpecMap().get("APIID"))));


        return StreamSupport.stream(repository.findAll(builder, Sort.by(Sort.Direction.ASC, "place"))
                .spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllMiddlewareNames() {
        return repository.getNamesOfAllUsedMiddlewares();
    }
}
