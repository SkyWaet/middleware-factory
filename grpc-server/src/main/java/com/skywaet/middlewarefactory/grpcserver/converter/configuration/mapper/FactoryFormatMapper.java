package com.skywaet.middlewarefactory.grpcserver.converter.configuration.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

public class FactoryFormatMapper {

    @Bean("factoryFormatMapper")
    public ModelMapper getFactoryFormatMapper() {
        ModelMapper factoryFormatMapper = new ModelMapper();
        factoryFormatMapper.getConfiguration().setMatchingStrategy(STRICT);

        return factoryFormatMapper;
    }
}
