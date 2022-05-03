package com.skywaet.middlewarefactory.grpcserver.unit.service;


import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;


public class FactoryServiceTest {

    private final ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
    private final IConfigurationStorageService configurationService = Mockito.mock(IConfigurationStorageService.class);


    @Test
    void shouldCreateCorrectChain() {

    }


}
