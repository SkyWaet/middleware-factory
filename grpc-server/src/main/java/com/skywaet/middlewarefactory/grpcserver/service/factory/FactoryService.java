package com.skywaet.middlewarefactory.grpcserver.service.factory;


import com.skywaet.middlewarefactory.grpcserver.middleware.BaseMiddleware;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.service.configuration.IConfigurationService;
import coprocess.CoprocessMiniRequestObject;
import coprocess.CoprocessObject;
import coprocess.DispatcherGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.List;

@GRpcService
@AllArgsConstructor
public class FactoryService extends DispatcherGrpc.DispatcherImplBase implements ApplicationContextAware {

    private final IConfigurationService configurationService;
    @Setter
    private ApplicationContext applicationContext;

    @Override
    public void dispatch(CoprocessObject.Object request,
                         StreamObserver<CoprocessObject.Object> responseObserver) {

        CoprocessMiniRequestObject.MiniRequestObject requestData = request.getRequest();

        List<EndpointMiddlewareBinding> middlewareConfigurations = configurationService.getMiddlewaresForRequest(request);

        CoprocessObject.Object result = null;

        if (!CollectionUtils.isEmpty(middlewareConfigurations)) {
            middlewareConfigurations.stream()
                    .map(config -> {
                        BaseMiddleware middleware = applicationContext.getBean(config.getMiddleware().getName(),
                                BaseMiddleware.class);
                        return middleware.process(request, config.getParams());
                    })
                    .reduce(this::mergeObjects)
                    .ifPresent(responseObserver::onNext);
        }

        responseObserver.onCompleted();
    }

    private CoprocessObject.Object mergeObjects(CoprocessObject.Object accumulator, CoprocessObject.Object newValue) {
        CoprocessObject.Object.Builder accumulatorBuilder = accumulator.toBuilder();
        CoprocessMiniRequestObject.MiniRequestObject newValRequest = newValue.getRequest();
        CoprocessMiniRequestObject.MiniRequestObject.Builder miniRequestBuilder = accumulatorBuilder.getRequestBuilder();
        miniRequestBuilder.putAllSetHeaders(newValRequest.getSetHeadersMap());
        miniRequestBuilder.addAllDeleteHeaders(newValue.getRequest().getDeleteHeadersList());

        miniRequestBuilder.putAllAddParams(newValue.getRequest().getAddParamsMap());
        miniRequestBuilder.addAllDeleteParams(newValue.getRequest().getDeleteParamsList());
        miniRequestBuilder.putAllExtendedParams(newValRequest.getExtendedParamsMap());

        if (miniRequestBuilder.getBody() == null || newValRequest.getBody() != null) {
            miniRequestBuilder.setBody(newValRequest.getBody());
        }
        if (newValRequest.getReturnOverrides() != null) {
            miniRequestBuilder.setReturnOverrides(newValRequest.getReturnOverrides());
        }
        return accumulatorBuilder.build();
    }
}
