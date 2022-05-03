package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.reciever;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;

public interface ConfigurationReceiver {

    void processNotification(FactoryNotification notification);
}
