package com.skywaet.middlewarefactory.grpcserver.service.factory;

import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.exception.MiddlewareNotFoundException;
import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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

    public abstract IConfigurationService getConfigurationService();

    @Override
    public BaseRequest processRequest(BaseRequest request) {
        List<EndpointMiddlewareBinding> middlewareConfigurations = getConfigurationService().getMiddlewaresForRequest(request);
        if (!CollectionUtils.isEmpty(middlewareConfigurations)) {
            BaseRequest result = request;
            for (EndpointMiddlewareBinding config : middlewareConfigurations) {
                BaseMiddleware middleware = middlewareMap.get(config.getMiddleware().getName());
                if (middleware == null) {
                    String middlewareName = config.getMiddleware().getName();
                    try {
                        addNewMiddleware(middlewareName);
                    } catch (NoSuchBeanDefinitionException ex) {
                        log.error("Middleware {} not found", middlewareName);
                        throw new MiddlewareNotFoundException(middlewareName);
                    }
                }
                result = result.mergeWith(middleware.process(request, config.getParams()));
            }
            return result;
        }
        return request;
    }

    @EventListener
    public void initMiddlewareMap(ApplicationReadyEvent event) {
        getConfigurationService().getAllMiddlewareNames().forEach(name ->
                middlewareMap.put(name, applicationContext.getBean(name,
                        BaseMiddleware.class)));
    }

    private void addNewMiddleware(String middlewareName) throws NoSuchBeanDefinitionException {
        middlewareMap.put(middlewareName, applicationContext.getBean(middlewareName,
                BaseMiddleware.class));

    }
}
