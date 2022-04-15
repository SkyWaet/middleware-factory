package com.skywaet.middlewarefactory.grpcserver.client;

import org.springframework.boot.context.event.ApplicationReadyEvent;

public interface ConfigurationClient {
    void sendInitialConfigurationRequest(ApplicationReadyEvent event);

    void sendMiddlewareNames();


}
