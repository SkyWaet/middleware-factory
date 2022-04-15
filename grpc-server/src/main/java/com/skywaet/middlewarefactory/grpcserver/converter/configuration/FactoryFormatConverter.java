package com.skywaet.middlewarefactory.grpcserver.converter.configuration;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointMiddlewareBindingDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpoint;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactoryFormatConverter {
    @Qualifier("factoryFormatMapper")
    private final ModelMapper factoryFormatMapper;

    public FactoryEndpoint fromFactoryEndpointDto(FactoryEndpointDto dto) {
        return factoryFormatMapper.map(dto, FactoryEndpoint.class);
    }

    public FactoryEndpoint fromFactoryEndpointShortDto(FactoryEndpointShortDto dto) {
        return factoryFormatMapper.map(dto, FactoryEndpoint.class);
    }

    public FactoryEndpointMiddlewareBinding fromFactoryEndpointMiddlewareBindingDto(FactoryEndpointMiddlewareBindingDto dto) {
        return factoryFormatMapper.map(dto, FactoryEndpointMiddlewareBinding.class);
    }

    public FactoryEndpointMiddlewareBinding fromFactoryEndpointMiddlewareBinding(EndpointMiddlewareBinding model) {
        return factoryFormatMapper.map(model, FactoryEndpointMiddlewareBinding.class);
    }

}
