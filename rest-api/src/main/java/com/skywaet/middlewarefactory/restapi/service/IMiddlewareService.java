package com.skywaet.middlewarefactory.restapi.service;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.MiddlewareDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMiddlewareService {
    Page<MiddlewareDto> list(Pageable pageable);

    MiddlewareDto create(MiddlewareDto dto);

    MiddlewareDto findById(Long id);

    MiddlewareDto updateById(Long id, MiddlewareDto dto);

    void deleteById(Long id);
}
