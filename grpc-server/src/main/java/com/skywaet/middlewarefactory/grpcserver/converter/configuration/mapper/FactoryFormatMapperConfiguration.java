package com.skywaet.middlewarefactory.grpcserver.converter.configuration.mapper;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpoint;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.util.UrlToRegexpConversionUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class FactoryFormatMapperConfiguration {

    @Bean("factoryFormatMapper")
    public ModelMapper getFactoryFormatMapper() {
        ModelMapper factoryFormatMapper = new ModelMapper();
        factoryFormatMapper.getConfiguration().setMatchingStrategy(STRICT);

        Converter<String, String> uriConverter = ctx -> ctx != null && ctx.getSource() != null
                ? UrlToRegexpConversionUtils.createRegexpForUrl(ctx.getSource()) : null;
        factoryFormatMapper.createTypeMap(FactoryEndpointDto.class, FactoryEndpoint.class)
                .addMappings(mapping -> {
                    mapping.using(uriConverter).map(FactoryEndpointDto::getUri, FactoryEndpoint::setUri);
                });
        factoryFormatMapper.createTypeMap(FactoryEndpointShortDto.class, FactoryEndpoint.class)
                .addMappings(mapping -> {
                    mapping.using(uriConverter).map(FactoryEndpointShortDto::getUri, FactoryEndpoint::setUri);
                });
        factoryFormatMapper.createTypeMap(EndpointMiddlewareBinding.class, FactoryEndpointMiddlewareBinding.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getMiddleware().getName(), FactoryEndpointMiddlewareBinding::setMiddlewareName);
                });

        return factoryFormatMapper;
    }
}
