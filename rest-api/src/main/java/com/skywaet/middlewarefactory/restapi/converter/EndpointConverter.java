package com.skywaet.middlewarefactory.restapi.converter;

import com.skywaet.middlewarefactory.factorycommon.model.Endpoint;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointExtendedDto;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EndpointConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public EndpointDto toDto(Endpoint endpoint) {
        return modelMapper.map(endpoint, EndpointDto.class);
    }

    public EndpointExtendedDto toExtendedDto(Endpoint endpoint) {
        return modelMapper.map(endpoint, EndpointExtendedDto.class);
    }

    public Endpoint fromDto(EndpointDto dto) {
        Endpoint result = modelMapper.map(dto, Endpoint.class);
        if (CollectionUtils.isNotEmpty(result.getMiddlewares())) {
            result.getMiddlewares().forEach(binding -> binding.setEndpoint(result));
        }
        return result;
    }
}
