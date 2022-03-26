package com.skywaet.middlewarefactory.grpcserver.service.factory;

import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                result = result.mergeWith(middleware.process(request, config.getParams()));
            }
            return result;
        }
        return request;
    }

    @PostConstruct
    private void initMiddlewareMap() {
        getConfigurationService().getAllMiddlewareNames().forEach(name ->
                middlewareMap.put(name, applicationContext.getBean(name,
                        BaseMiddleware.class)));
    }
}
