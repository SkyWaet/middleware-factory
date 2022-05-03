package com.skywaet.middlewarefactory.restapi.converter;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.MiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.model.Endpoint;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import liquibase.pro.packaged.F;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointMiddlewareBindingDto;


import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class FactoryDtoConverter {

    private final ModelMapper mapper = new ModelMapper();

    public MiddlewareDto toMiddlewareDto(FactoryMiddlewareDto dto) {
        return mapper.map(dto, MiddlewareDto.class);
    }

    public FactoryEndpointDto toFactoryEndpoint(Endpoint endpoint) {
        return mapper.map(endpoint, FactoryEndpointDto.class);
    }

    public FactoryEndpointDto toFactoryEndpoint(EndpointDto endpoint) {
        return mapper.map(endpoint, FactoryEndpointDto.class);
    }

    public FactoryEndpointShortDto toFactoryEndpointShortDto(EndpointDto endpoint) {
        return mapper.map(endpoint, FactoryEndpointShortDto.class);
    }

    @PostConstruct
    private void initMapper() {
        mapper.createTypeMap(EndpointMiddlewareBinding.class, FactoryEndpointMiddlewareBindingDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getMiddleware().getName(), FactoryEndpointMiddlewareBindingDto::setMiddlewareName);
                });
    }
}
