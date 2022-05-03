package com.skywaet.middlewarefactory.restapi.factory.receiver.kafka;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.restapi.factory.receiver.FactoryMessageReceiver;
import com.skywaet.middlewarefactory.restapi.factory.service.IFactoryConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaFactoryReceiver implements FactoryMessageReceiver {
    private final IFactoryConfigurationService factoryConfigurationService;

    @Override
    @KafkaListener(topics = {"${kafka.factory-request-topic-name:factory-request}"}, groupId = "${kafka.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void getMessageFromFactory(FactoryNotification notification) {
        factoryConfigurationService.processNotificationFromFactory(notification);
    }

}
