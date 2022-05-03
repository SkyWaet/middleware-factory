package com.skywaet.middlewarefactory.restapi.factory.service;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;

import java.util.List;

public interface IFactoryConfigurationService {
    void processNotificationFromFactory(FactoryNotification notification);

    void sendInitialConfiguration();

    void processEndpointAddOrUpdate(FactoryNotification.EventEnum eventType, EndpointExtendedDto dto);

    void processEndpointDeletion(EndpointDto dto);

    void saveMiddlewaresInfo(List<FactoryMiddlewareDto> middlewares);
}
