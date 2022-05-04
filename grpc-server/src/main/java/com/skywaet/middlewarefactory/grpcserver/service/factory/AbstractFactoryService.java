package com.skywaet.middlewarefactory.grpcserver.service.factory;

import com.skywaet.middlewarefactory.grpcserver.exception.notfound.MiddlewareNotFoundException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.model.FactoryEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationStorageService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractFactoryService implements MiddlewareFactory, ApplicationContextAware {

    private final Map<String, BaseMiddleware> middlewareMap = new HashMap<>();

    @Setter
    private ApplicationContext applicationContext;

    public abstract IConfigurationStorageService getConfigurationService();

    @Override
    public BaseRequest processRequest(BaseRequest request) {
        List<FactoryEndpointMiddlewareBinding> middlewareConfigurations = getConfigurationService().getMiddlewaresForRequest(request);
        if (!CollectionUtils.isEmpty(middlewareConfigurations)) {
            //BaseRequest result = request;
            for (FactoryEndpointMiddlewareBinding config : middlewareConfigurations) {
                BaseMiddleware middleware = middlewareMap.get(config.getMiddlewareName());
                if (middleware == null) {
                    log.error("Middleware {} not found", config.getMiddlewareName());
                    throw new MiddlewareNotFoundException(config.getMiddlewareName());
                }
               middleware.process(request, config.getParams());
            }
           // return result;
        }
        return request;
    }

    @EventListener
    public void initMiddlewareMap(ApplicationReadyEvent event) {
        getConfigurationService().getAvailableMiddlewareNames().forEach(name ->
                middlewareMap.put(name, applicationContext.getBean(name,
                        BaseMiddleware.class)));
    }
}
