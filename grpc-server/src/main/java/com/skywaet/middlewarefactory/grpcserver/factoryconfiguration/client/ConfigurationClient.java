package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.client;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;

public interface ConfigurationClient {
    void sendMessage(FactoryNotification notification);

}
