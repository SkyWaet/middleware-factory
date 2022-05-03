package com.skywaet.middlewarefactory.grpcserver.service.configuration.impl;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
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

    private final Map<FactoryEndpoint, Set<String>> endpointUriRegexps = new HashMap<>();
    private final Map<String, Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>>> configurationsForEndpoint = new HashMap<>();
    private final FactoryFormatConverter factoryFormatConverter;

    @Value("${factory.available-middlewares}")
    @Getter
    private List<String> availableMiddlewareNames;


    @Override
    public List<FactoryEndpointMiddlewareBinding> getMiddlewaresForRequest(BaseRequest request) {
        if (request instanceof TykRequest) {
            TykRequest convertedRequest = (TykRequest) request;
            FactoryEndpoint endpointOfRequest = new FactoryEndpoint(convertedRequest.getMethod(), convertedRequest.getApiId());
            String requestPath = request.getRequestUrl().split("\\?")[0];
            Set<String> uriRegexps = endpointUriRegexps.get(endpointOfRequest);
            if (CollectionUtils.isNotEmpty(uriRegexps)) {
                Optional<String> correctRegexp = uriRegexps.stream().filter(requestPath::matches).findAny();
                if (correctRegexp.isPresent()) {
                    Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>> configurationsForAllPhases = configurationsForEndpoint
                            .get(correctRegexp.get());
                    if (configurationsForAllPhases != null) {
                        return new ArrayList<>(configurationsForAllPhases.getOrDefault(convertedRequest.getRequestPhase(), new TreeSet<>()));
                    }
                }
            }
            return new ArrayList<>();
        }
        throw new IllegalArgumentException("Wrong request type");
    }

    @Override
    public void saveConfigurationForEndpoint(FactoryEndpointDto dto) {
        FactoryEndpoint endpoint = factoryFormatConverter.fromFactoryEndpointDto(dto);
        String parsedPath = UrlToRegexpConversionUtils.createRegexpForUrl(dto.getUri());
        endpointUriRegexps.putIfAbsent(endpoint, new HashSet<>());
        Set<String> regexpsForApiAndEndpoint = endpointUriRegexps.get(endpoint);
        regexpsForApiAndEndpoint.add(parsedPath);
        log.debug("Endpoint {} {} of API with id {} added", endpoint.getMethod(), dto.getUri(), endpoint.getApiId());
        configurationsForEndpoint.putIfAbsent(parsedPath, new HashMap<>());
        if (CollectionUtils.isNotEmpty(dto.getMiddlewares())) {
            dto.getMiddlewares().stream().map(factoryFormatConverter::fromFactoryEndpointMiddlewareBindingDto)
                    .forEach(binding -> {
                        Map<Phase, TreeSet<FactoryEndpointMiddlewareBinding>> configurations
                                = configurationsForEndpoint.get(parsedPath);
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
        Set<String> regexpsForEndpoint = endpointUriRegexps.get(endpointToDelete);
        if (regexpsForEndpoint != null) {
            boolean isRemoved = regexpsForEndpoint.remove(parsedPath);
            if (isRemoved) {
                configurationsForEndpoint.remove(parsedPath);
                if (CollectionUtils.isEmpty(regexpsForEndpoint)) {
                    endpointUriRegexps.remove(endpointToDelete);
                }
                log.debug("Endpoint {} {} of API with id {} deleted successfully", endpointToDelete.getMethod(),
                        dto.getUri(), endpointToDelete.getApiId());
                return;
            }
        }
        log.error("Endpoint {} {} of API with id {} does not exist", endpointToDelete.getMethod(),
                dto.getUri(), endpointToDelete.getApiId());
    }

}
