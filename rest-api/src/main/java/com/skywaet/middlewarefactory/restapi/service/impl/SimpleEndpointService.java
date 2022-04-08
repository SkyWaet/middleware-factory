package com.skywaet.middlewarefactory.restapi.service.impl;

import com.skywaet.middlewarefactory.factorycommon.model.Endpoint;
import com.skywaet.middlewarefactory.restapi.converter.EndpointConverter;
import com.skywaet.middlewarefactory.restapi.exception.notfound.EndpointNotFoundException;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.restapi.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.restapi.repository.EndpointRepository;
import com.skywaet.middlewarefactory.restapi.service.IEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SimpleEndpointService implements IEndpointService {

    private final EndpointRepository endpointRepository;
    private final EndpointConverter endpointConverter;

    @Override
    public Page<EndpointDto> list(Pageable pageable) {
        return endpointRepository.findAll(pageable).map(endpointConverter::toDto);
    }

    @Override
    @Transactional
    public EndpointExtendedDto create(EndpointExtendedDto dto) {
        return endpointConverter.toExtendedDto(endpointRepository.save(endpointConverter.fromDto(dto)));
    }

    @Override
    @Transactional
    public EndpointExtendedDto findById(Long id) {
        return endpointConverter.toExtendedDto(endpointRepository.findById(id)
                .orElseThrow(EndpointNotFoundException::new));
    }

    @Override
    @Transactional
    public EndpointExtendedDto updateById(Long id, EndpointExtendedDto dto) {
        if (!endpointRepository.existsById(id)) {
            throw new EndpointNotFoundException();
        }
        Endpoint newVersion = endpointConverter.fromDto(dto);
        newVersion.setId(id);
        return endpointConverter.toExtendedDto(endpointRepository.save(newVersion));
    }

    @Override
    public void deleteById(Long id) {
        endpointRepository.findById(id)
                .ifPresentOrElse(
                        endpoint -> endpointRepository.deleteById(id),
                        EndpointNotFoundException::new);
    }
}
