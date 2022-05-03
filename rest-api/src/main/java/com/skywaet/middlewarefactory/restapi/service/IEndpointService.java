package com.skywaet.middlewarefactory.restapi.service;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.factorycommon.model.Endpoint;
import org.springframework.data.domain.Page;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import org.springframework.data.domain.Pageable;

public interface IEndpointService {
    Page<EndpointDto> list(Pageable pageable);

    Page<FactoryEndpointDto> processFactoryRequest(Pageable pageable);

    EndpointExtendedDto create(EndpointExtendedDto dto);

    EndpointExtendedDto findById(Long id);

    EndpointExtendedDto updateById(Long id, EndpointExtendedDto dto);

    EndpointDto deleteById(Long id);
}
