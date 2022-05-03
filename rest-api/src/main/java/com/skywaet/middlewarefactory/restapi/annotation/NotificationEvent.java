package com.skywaet.middlewarefactory.restapi.annotation;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification.EventEnum;

public @interface NotificationEvent {
    EventEnum eventType();
}
