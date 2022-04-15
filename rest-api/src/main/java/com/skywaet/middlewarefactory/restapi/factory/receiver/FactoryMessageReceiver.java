package com.skywaet.middlewarefactory.restapi.factory.receiver;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;

public interface FactoryMessageReceiver {

    void getMessageFromFactory(FactoryNotification notification);

}
