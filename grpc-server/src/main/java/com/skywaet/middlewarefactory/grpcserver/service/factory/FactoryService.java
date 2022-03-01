package com.skywaet.middlewarefactory.grpcserver.service.factory;


import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import com.skywaet.middlewarefactory.grpcserver.util.MergeObjectsUtils;
import coprocess.CoprocessObject;
import coprocess.DispatcherGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@GrpcService
@AllArgsConstructor
public class FactoryService extends DispatcherGrpc.DispatcherImplBase implements ApplicationContextAware {

    private final IConfigurationService configurationService;
    private final Map<String, BaseMiddleware> middlewareMap;
    private final MergeObjectsUtils mergeObjectsUtils;
    @Setter
    private ApplicationContext applicationContext;


    @Override
    public void dispatch(CoprocessObject.Object request,
                         StreamObserver<CoprocessObject.Object> responseObserver) {

        List<EndpointMiddlewareBinding> middlewareConfigurations = configurationService.getMiddlewaresForRequest(request);


        if (!CollectionUtils.isEmpty(middlewareConfigurations)) {
            middlewareConfigurations.stream()
                    .map(config -> {
                        BaseMiddleware middleware = middlewareMap.get(config.getMiddleware().getName());
                        return middleware.process(request, config.getParams());
                    })
                    .reduce(mergeObjectsUtils::mergeObjects)
                    .ifPresent(responseObserver::onNext);
        }

        responseObserver.onCompleted();
    }


    @PostConstruct
    private void fillMiddlewareMap() {
        configurationService.getAllMiddlewareNames().forEach(name -> {
            middlewareMap.put(name, applicationContext.getBean(name,
                    BaseMiddleware.class));
        });
    }

}
