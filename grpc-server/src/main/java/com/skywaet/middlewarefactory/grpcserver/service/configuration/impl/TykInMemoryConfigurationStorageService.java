package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.model.Phase;
import com.skywaet.middlewarefactory.grpcserver.converter.configuration.FactoryFormatConverter;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpoint;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationStorageService;
import com.skywaet.middlewarefactory.grpcserver.util.UrlToRegexpConversionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@ConditionalOnProperty(name = "factory.configuration-storage", havingValue = "in-memory")
@AllArgsConstructor
@Slf4j
public class TykInMemoryConfigurationStorageService implements IConfigurationStorageService {

    private final Map<FactoryEndpoint, Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>>> configurationsForEndpoint = new HashMap<>();
    private final FactoryFormatConverter factoryFormatConverter;

    @Value("${factory.available-middlewares}")
    @Getter
    private List<String> availableMiddlewareNames;


    @Override
    public List<FactoryEndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request) {
        if (request instanceof TykRequest) {
            TykRequest convertedRequest = (TykRequest) request;
            String requestPath = request.getRequestUrl().split("\\?")[0];
            FactoryEndpoint requestEndpoint = new FactoryEndpoint(convertedRequest.getMethod(),
                    convertedRequest.getApiId(), requestPath);
            Optional<FactoryEndpoint> endpointOfRequest = Optional.of(requestEndpoint);
//                    configurationsForEndpoint.keySet()
//                    .parallelStream().filter(endpoint -> convertedRequest.getMethod().equals(endpoint.getMethod())
//                            && convertedRequest.getApiId().equals(endpoint.getApiId())
//                            && requestPath.matches(endpoint.getUri())
//                    ).findAny();

            if (endpointOfRequest.isPresent()) {
                Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>> configurationsForAllPhases = configurationsForEndpoint
                        .get(endpointOfRequest.get());
                if (configurationsForAllPhases != null) {
                    return new ArrayList<>(configurationsForAllPhases.getOrDefault(convertedRequest.getRequestPhase(), new TreeSet<>()));
                }

            }
            return new ArrayList<>();
        }
        throw new IllegalArgumentException("Wrong request type");
    }

    @Override
    public void saveConfigurationForEndpoint(FactoryEndpointDto dto) {
        FactoryEndpoint endpoint = factoryFormatConverter.fromFactoryEndpointDto(dto);
        log.debug("Endpoint {} {} of API with id {} added", endpoint.getMethod(), endpoint.getUri(), endpoint.getApiId());
        configurationsForEndpoint.putIfAbsent(endpoint, new HashMap<>());
        if (CollectionUtils.isNotEmpty(dto.getMiddlewares())) {
            dto.getMiddlewares().stream().map(factoryFormatConverter::fromFactoryEndpointMiddlewareBindingDto)
                    .forEach(binding -> {
                        Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>> configurations
                                = configurationsForEndpoint.get(endpoint);
                        configurations.putIfAbsent(binding.getPhase(), new TreeSet<>());
                        configurations.get(binding.getPhase()).add(binding);
                    });
            log.debug("Added {} middleware bindings for endpoint {} {} of API with id {}",
                    dto.getMiddlewares().size(), endpoint.getMethod(), dto.getUri(), endpoint.getApiId());
        }

    }

    @Override
    public void deleteConfigurationForEndpoint(FactoryEndpointShortDto dto) {
        FactoryEndpoint endpointToDelete = factoryFormatConverter.fromFactoryEndpointShortDto(dto);
        String parsedPath = UrlToRegexpConversionUtils.createRegexpForUrl(dto.getUri());
        log.debug("Trying to remove configuration for endpoint {} {} of API with id {} ", endpointToDelete.getMethod(),
                dto.getUri(), endpointToDelete.getApiId());
        if (configurationsForEndpoint.remove(endpointToDelete) != null) {
            log.debug("Endpoint {} {} of API with id {} deleted successfully", endpointToDelete.getMethod(),
                    dto.getUri(), endpointToDelete.getApiId());
            return;

        }
        log.error("Endpoint {} {} of API with id {} does not exist", endpointToDelete.getMethod(),
                endpointToDelete.getUri(), endpointToDelete.getApiId());
    }

}
