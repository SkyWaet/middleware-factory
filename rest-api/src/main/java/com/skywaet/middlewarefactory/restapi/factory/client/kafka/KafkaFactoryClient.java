package com.skywaet.middlewarefactory.restapi.factory.client.kafka;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.restapi.factory.client.FactoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaFactoryClient implements FactoryClient {

    private final KafkaTemplate<String, FactoryNotification> kafkaTemplate;
    @Value("${kafka.factory-configuration-topic-name:factory-configuration}")
    private String factoryConfigurationTopicName;

    @Override
    public void sendMessage(FactoryNotification notification) {
        kafkaTemplate.send(factoryConfigurationTopicName, notification);
    }

}
