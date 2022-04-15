package com.skywaet.middlewarefactory.restapi.factory.client;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;

public interface FactoryClient {
    void sendMessage(FactoryNotification notification);
}
