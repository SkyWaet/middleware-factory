package com.skywaet.middlewarefactory.restapi.aspect;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.EndpointExtendedDto;
import com.skywaet.middlewarefactory.restapi.annotation.NotificationEvent;
import com.skywaet.middlewarefactory.restapi.factory.service.IFactoryConfigurationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
@Order
@Slf4j
public class NotificationAspect {

    private final IFactoryConfigurationService factoryConfigurationService;

    @AfterReturning(value = "@annotation(event)", returning = "retVal")
    public void processEvent(NotificationEvent event, Object retVal) {
        switch (event.eventType()) {
            case ADD_ENDPOINT:
            case UPDATE_ENDPOINT:
                EndpointExtendedDto dtoToUpdate = (EndpointExtendedDto) retVal;
                factoryConfigurationService.processEndpointAddOrUpdate(event.eventType(), dtoToUpdate);
                return;
            case DELETE_ENDPOINT:
                EndpointDto dtoToDelete = (EndpointDto) retVal;
                factoryConfigurationService.processEndpointDeletion(dtoToDelete);
                return;
            default:
                log.error("Wrong type of notification: {}", event.eventType());
        }
    }
}
