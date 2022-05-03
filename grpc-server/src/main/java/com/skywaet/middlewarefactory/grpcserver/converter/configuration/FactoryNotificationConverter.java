package com.skywaet.middlewarefactory.grpcserver.converter.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointListDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryEndpointShortDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.grpcserver.exception.factory.CreatingNotificationException;
import com.skywaet.middlewarefactory.grpcserver.exception.factory.ParsingNotificationMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class FactoryNotificationConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public FactoryNotification createNotification(FactoryNotification.EventEnum eventType, Object content) {
        try {
            byte[] middlewareNamesAsBytes =
                    Base64Utils.encode(mapper.writeValueAsBytes(content));
            return new FactoryNotification()
                    .event(eventType)
                    .message(middlewareNamesAsBytes);
        } catch (JsonProcessingException e) {
            log.error("Error while creating notification: {}", e.getMessage());
            throw new CreatingNotificationException(e.getMessage());
        }
    }

    public List<FactoryEndpointDto> getListOfEndpointsFromNotification(FactoryNotification notification) {
        try {
            FactoryEndpointListDto content = mapper.readValue(Base64Utils.decode(notification.getMessage()), FactoryEndpointListDto.class);
            return content.getContent();
        } catch (RuntimeException | IOException e) {
            log.error("Error while parsing notification: {}", e.getMessage());
            throw new ParsingNotificationMessageException(e.getMessage());
        }
    }

    public FactoryEndpointDto getEndpointFromNotification(FactoryNotification notification) {
        try {
            return mapper.readValue(Base64Utils.decode(notification.getMessage()), FactoryEndpointDto.class);
        } catch (RuntimeException | IOException e) {
            log.error("Error while parsing notification: {}", e.getMessage());
            throw new ParsingNotificationMessageException(e.getMessage());
        }
    }

    public FactoryEndpointShortDto getShortEndpointFromNotification(FactoryNotification notification) {
        try {
            return mapper.readValue(Base64Utils.decode(notification.getMessage()), FactoryEndpointShortDto.class);
        } catch (RuntimeException | IOException e) {
            log.error("Error while parsing notification: {}", e.getMessage());
            throw new ParsingNotificationMessageException(e.getMessage());
        }
    }
}
