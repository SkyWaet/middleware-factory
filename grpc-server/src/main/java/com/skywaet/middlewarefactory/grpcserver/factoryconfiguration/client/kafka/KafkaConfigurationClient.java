package com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.client.kafka;

import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.grpcserver.factoryconfiguration.client.ConfigurationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "factory.configuration-source", havingValue = "kafka")
@RequiredArgsConstructor
public class KafkaConfigurationClient implements ConfigurationClient {

    private final KafkaTemplate<String, FactoryNotification> kafkaTemplate;

    @Value("${kafka.factory-request-topic-name:factory-request}")
    private String factoryRequestTopicName;

    @Override
    public void sendMessage(FactoryNotification notification) {
        kafkaTemplate.send(factoryRequestTopicName, notification);
    }
}
