package com.skywaet.middlewarefactory.restapi.service.impl;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.MiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.model.Middleware;
import com.skywaet.middlewarefactory.restapi.converter.MiddlewareConverter;
import com.skywaet.middlewarefactory.restapi.exception.notfound.MiddlewareNotFoundException;
import com.skywaet.middlewarefactory.restapi.repository.MiddlewareRepository;
import com.skywaet.middlewarefactory.restapi.service.IMiddlewareService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleMiddlewareService implements IMiddlewareService {
    private final MiddlewareRepository middlewareRepository;
    private final MiddlewareConverter middlewareConverter;

    @Override
    public Page<MiddlewareDto> list(Pageable pageable) {
        return middlewareRepository.findAll(pageable).map(middlewareConverter::toDto);
    }

    @Override
    public MiddlewareDto create(MiddlewareDto dto) {
        return middlewareConverter.toDto(middlewareRepository.save(middlewareConverter.from(dto)));
    }

    @Override
    public MiddlewareDto findById(Long id) {
        return middlewareRepository.findById(id).map(middlewareConverter::toDto).orElseThrow(MiddlewareNotFoundException::new);
    }

    @Override
    public MiddlewareDto updateById(Long id, MiddlewareDto dto) {
        if (!middlewareRepository.existsById(id)) {
            throw new MiddlewareNotFoundException();
        }
        Middleware newMiddleware = middlewareConverter.from(dto);
        newMiddleware.setId(id);
        return middlewareConverter.toDto(middlewareRepository.save(newMiddleware));
    }

    @Override
    public MiddlewareDto doAddOrUpdate(MiddlewareDto dto) {
        Optional<Middleware> existingMiddleware = middlewareRepository.getMiddlewareByName(dto.getName());
        return existingMiddleware.isPresent()
                ? updateById(existingMiddleware.get().getId(), dto)
                : create(dto);
    }

    @Override
    public void deleteById(Long id) {
        middlewareRepository.findById(id)
                .ifPresentOrElse(
                        middleware -> middlewareRepository.deleteById(id),
                        MiddlewareNotFoundException::new
                );
    }
}
