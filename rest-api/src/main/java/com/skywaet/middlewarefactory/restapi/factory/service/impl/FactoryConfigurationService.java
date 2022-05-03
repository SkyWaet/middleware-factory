package com.skywaet.middlewarefactory.restapi.factory.service.impl;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointListDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.restapi.converter.FactoryDtoConverter;
import com.skywaet.middlewarefactory.restapi.converter.FactoryNotificationConverter;
import com.skywaet.middlewarefactory.restapi.factory.client.FactoryClient;
import com.skywaet.middlewarefactory.restapi.factory.service.IFactoryConfigurationService;
import com.skywaet.middlewarefactory.restapi.service.IEndpointService;
import com.skywaet.middlewarefactory.restapi.service.IMiddlewareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FactoryConfigurationService implements IFactoryConfigurationService {
    private final FactoryClient factoryClient;
    private final IEndpointService endpointService;
    private final IMiddlewareService middlewareService;
    private final FactoryDtoConverter factoryDtoConverter;
    private final FactoryNotificationConverter factoryNotificationConverter;
    private static final Pageable INITIAL_PAGEABLE = Pageable.ofSize(20).first();

    @Override
    public void processNotificationFromFactory(FactoryNotification notification) {
        switch (notification.getEvent()) {
            case INITIAL_LOAD:
                sendInitialConfiguration();
                return;
            case MIDDLEWARE_NAMES:
                List<FactoryMiddlewareDto> content = factoryNotificationConverter.getMessageForMiddlewareNamesEvent(notification);
                saveMiddlewaresInfo(content);
                return;
            default:
                log.error("Wrong notification event type: {}", notification.getEvent());
        }
    }

    @Override
    public void sendInitialConfiguration() {
        Pageable currentPageable = INITIAL_PAGEABLE;
        Page<FactoryEndpointDto> currentPage;
        do {
            currentPage = endpointService.processFactoryRequest(currentPageable);
            FactoryEndpointListDto content = new FactoryEndpointListDto().content(currentPage.getContent());
            FactoryNotification preparedNotification = factoryNotificationConverter
                    .createNotification(FactoryNotification.EventEnum.INITIAL_LOAD, content);
            factoryClient.sendMessage(preparedNotification);
            currentPageable = currentPage.nextPageable();

        } while (currentPage.hasNext());
    }

    @Override
    public void processEndpointAddOrUpdate(FactoryNotification.EventEnum eventType, EndpointExtendedDto dto) {
        FactoryEndpointDto factoryEndpoint = factoryDtoConverter.toFactoryEndpoint(endpointService.findById(dto.getId()));
        FactoryNotification notification = factoryNotificationConverter.createNotification(eventType, factoryEndpoint);
        factoryClient.sendMessage(notification);
    }

    @Override
    public void processEndpointDeletion(EndpointDto dto) {
        FactoryEndpointShortDto factoryEndpointShortDto = factoryDtoConverter.toFactoryEndpointShortDto(dto);
        FactoryNotification notification = factoryNotificationConverter.createNotification(FactoryNotification.EventEnum.DELETE_ENDPOINT,
                factoryEndpointShortDto);
        factoryClient.sendMessage(notification);
    }

    @Override
    public void saveMiddlewaresInfo(List<FactoryMiddlewareDto> middlewares) {
        middlewares.forEach(middleware ->
                middlewareService.doAddOrUpdate(factoryDtoConverter.toMiddlewareDto(middleware)));
    }
}
