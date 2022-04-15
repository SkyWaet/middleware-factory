package com.skywaet.middlewarefactory.grpcserver.service.factory.tyk;

import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import com.skywaet.middlewarefactory.grpcserver.service.factory.AbstractFactoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TykFactoryService extends AbstractFactoryService {

    @Getter
    private final IConfigurationService configurationService;

}
