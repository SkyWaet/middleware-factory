package com.skywaet.middlewarefactory.restapi.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryMiddlewareListDto;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.FactoryNotification;
import com.skywaet.middlewarefactory.restapi.exception.factory.CreatingNotificationException;
import com.skywaet.middlewarefactory.restapi.exception.factory.ParsingNotificationMessageException;
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

    public List<FactoryMiddlewareDto> getMessageForMiddlewareNamesEvent(FactoryNotification middlewareNamesEvent) {
        byte[] message = middlewareNamesEvent.getMessage();
        try {
            FactoryMiddlewareListDto parsedMessage = mapper.readValue(Base64Utils.decode(message), FactoryMiddlewareListDto.class);
            return parsedMessage.getContent();
        } catch (RuntimeException | IOException e) {
            log.error("Error while parsing notification: {}", e.getMessage());
            throw new ParsingNotificationMessageException(e.getMessage());
        }
    }
}
