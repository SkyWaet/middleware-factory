package com.skywaet.middlewarefactory.restapi.annotation;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification.EventEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NotificationEvent {
    EventEnum eventType();
}
