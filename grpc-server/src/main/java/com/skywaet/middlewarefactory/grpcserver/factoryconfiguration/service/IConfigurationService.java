package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.service;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.List;

public interface IConfigurationService {
    void processNotificationFromConfigurationService(FactoryNotification notification);

    void sendInitialConfigurationRequest(ApplicationReadyEvent event);

    void processInitialLoad(List<FactoryEndpointDto> initialConfiguration);

    void processEndpointAddOrUpdate(FactoryNotification.EventEnum eventType, FactoryEndpointDto dto);

    void processEndpointDeletion(FactoryEndpointShortDto dto);

    void sendMiddlewaresInfo();
}
