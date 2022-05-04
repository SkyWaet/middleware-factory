package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.service.impl;

import com.skywaet.middlewarefactory.grpcserver.converter.configuration.FactoryNotificationConverter;
import com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.client.ConfigurationClient;
import com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.service.IConfigurationService;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareListDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "factory.configuration-source", havingValue = "kafka")
public class FactoryConfigurationService implements IConfigurationService {

    private final IConfigurationStorageService configurationStorageService;
    private final ConfigurationClient configurationClient;
    private final FactoryNotificationConverter notificationConverter;

    @Override
    public void processNotificationFromConfigurationService(FactoryNotification notification) {
        switch (notification.getEvent()) {
            case INITIAL_LOAD:
                List<FactoryEndpointDto> content = notificationConverter.getListOfEndpointsFromNotification(notification);
                processInitialLoad(content);
                return;
            case ADD_ENDPOINT:
            case UPDATE_ENDPOINT:
                FactoryEndpointDto endpointDto = notificationConverter.getEndpointFromNotification(notification);
                processEndpointAddOrUpdate(notification.getEvent(), endpointDto);
                return;
            case DELETE_ENDPOINT:
                FactoryEndpointShortDto shortDto = notificationConverter.getShortEndpointFromNotification(notification);
                processEndpointDeletion(shortDto);
                return;
            default:
                log.error("Unknown event type: {}", notification.getEvent());
        }
    }

    @Override
    @EventListener
    public void sendInitialConfigurationRequest(ApplicationReadyEvent event) {
        FactoryNotification notification = notificationConverter
                .createNotification(FactoryNotification.EventEnum.INITIAL_LOAD, "no_value");
        configurationClient.sendMessage(notification);
    }

    @Override
    public void processInitialLoad(List<FactoryEndpointDto> initialConfiguration) {
        initialConfiguration.forEach(configurationStorageService::saveConfigurationForEndpoint);
    }

    @Override
    public void processEndpointAddOrUpdate(FactoryNotification.EventEnum eventType, FactoryEndpointDto dto) {
        configurationStorageService.saveConfigurationForEndpoint(dto);
    }

    @Override
    public void processEndpointDeletion(FactoryEndpointShortDto dto) {
        configurationStorageService.deleteConfigurationForEndpoint(dto);
    }

    @Override
    public void sendMiddlewaresInfo() {
        List<FactoryMiddlewareDto> middlewareDtos = configurationStorageService.getAvailableMiddlewareNames()
                .stream().map(name -> new FactoryMiddlewareDto().name(name)).collect(Collectors.toList());
        FactoryNotification notification = notificationConverter
                .createNotification(FactoryNotification.EventEnum.MIDDLEWARE_NAMES,
                        new FactoryMiddlewareListDto().content(middlewareDtos));
        configurationClient.sendMessage(notification);
    }
}
