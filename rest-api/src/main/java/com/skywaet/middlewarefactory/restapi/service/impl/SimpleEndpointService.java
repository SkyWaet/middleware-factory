package com.skywaet.middlewarefactory.restapi.service.impl;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.factorycommon.model.Endpoint;
import com.skywaet.middlewarefactory.restapi.annotation.NotificationEvent;
import com.skywaet.middlewarefactory.restapi.converter.EndpointConverter;
import com.skywaet.middlewarefactory.restapi.converter.FactoryDtoConverter;
import com.skywaet.middlewarefactory.restapi.exception.notfound.EndpointNotFoundException;
import com.skywaet.middlewarefactory.restapi.repository.EndpointRepository;
import com.skywaet.middlewarefactory.restapi.service.IEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEndpointService implements IEndpointService {

    private final EndpointRepository endpointRepository;
    private final EndpointConverter endpointConverter;
    private final FactoryDtoConverter factoryDtoConverter;

    @Override
    public Page<EndpointDto> list(Pageable pageable) {
        return endpointRepository.findAll(pageable).map(endpointConverter::toDto);
    }

    @Override
    @Transactional
    public Page<FactoryEndpointDto> processFactoryRequest(Pageable pageable) {
        return endpointRepository.findAll(pageable).map(factoryDtoConverter::toFactoryEndpoint);
    }

    @Override
    @Transactional
    @NotificationEvent(eventType = FactoryNotification.EventEnum.ADD_ENDPOINT)
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
    @NotificationEvent(eventType = FactoryNotification.EventEnum.UPDATE_ENDPOINT)
    public EndpointExtendedDto updateById(Long id, EndpointExtendedDto dto) {
        if (!endpointRepository.existsById(id)) {
            throw new EndpointNotFoundException();
        }
        Endpoint newVersion = endpointConverter.fromDto(dto);
        newVersion.setId(id);
        return endpointConverter.toExtendedDto(endpointRepository.save(newVersion));
    }

    @Override
    @NotificationEvent(eventType = FactoryNotification.EventEnum.DELETE_ENDPOINT)
    public EndpointDto deleteById(Long id) {
        Optional<Endpoint> endpoint = endpointRepository.findById(id);
        if (endpoint.isPresent()) {
            endpointRepository.deleteById(id);
            return endpointConverter.toDto(endpoint.get());
        }
        throw new EndpointNotFoundException();
    }
}
