package com.skywaet.middlewarefactory.restapi.converter;

import com.skywaet.middlewarefactory.factorycommon.model.Middleware;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.MiddlewareDto;

@Component
public class MiddlewareConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public MiddlewareDto toDto(Middleware middleware) {
        return modelMapper.map(middleware, MiddlewareDto.class);
    }

    public Middleware from(MiddlewareDto dto) {
        return modelMapper.map(dto, Middleware.class);
    }

}
