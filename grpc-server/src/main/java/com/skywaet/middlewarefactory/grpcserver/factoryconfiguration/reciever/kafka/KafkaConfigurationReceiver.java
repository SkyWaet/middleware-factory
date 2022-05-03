package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.reciever.kafka;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.reciever.ConfigurationReceiver;
import com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.service.IConfigurationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "factory.configuration-source", havingValue = "kafka")
@AllArgsConstructor
@Slf4j
public class KafkaConfigurationReceiver implements ConfigurationReceiver {

    private final IConfigurationService configurationService;

    @Override
    @KafkaListener(topics = {"${kafka.factory-configuration-topic-name:factory-configuration}"}, groupId = "${kafka.group-id}")
    public void processNotification(FactoryNotification notification) {
        configurationService.processNotificationFromConfigurationService(notification);
    }

}
