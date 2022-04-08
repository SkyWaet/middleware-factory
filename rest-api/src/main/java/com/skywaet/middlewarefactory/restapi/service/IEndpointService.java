package com.skywaet.middlewarefactory.restapi.service;

import org.springframework.data.domain.Page;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointExtendedDto;
import org.springframework.data.domain.Pageable;

public interface IEndpointService {
    Page<EndpointDto> list(Pageable pageable);

    EndpointExtendedDto create(EndpointExtendedDto dto);

    EndpointExtendedDto findById(Long id);

    EndpointExtendedDto updateById(Long id, EndpointExtendedDto dto);

    void deleteById(Long id);
}
