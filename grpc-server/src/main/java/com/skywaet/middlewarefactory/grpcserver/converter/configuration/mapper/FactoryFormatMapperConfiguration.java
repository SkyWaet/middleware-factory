package com.skywaet.middlewarefactory.grpcserver.converter.configuration.mapper;

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

        return factoryFormatMapper;
    }
}
