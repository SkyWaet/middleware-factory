package com.skywaet.middlewarefactory.grpcserver.service.grpc;

import com.skywaet.middlewarefactory.grpcserver.converter.tyk.TykRequestConverter;
import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;
import com.skywaet.middlewarefactory.grpcserver.exception.middleware.BaseMiddlewareException;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import com.skywaet.middlewarefactory.grpcserver.service.factory.MiddlewareFactory;
import coprocess.CoprocessObject;
import coprocess.CoprocessReturnOverrides;
import coprocess.DispatcherGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class TykRPCService extends DispatcherGrpc.DispatcherImplBase {

    private final MiddlewareFactory factory;
    private final TykRequestConverter converter;

    @Override
    public void dispatch(CoprocessObject.Object request,
                         StreamObserver<CoprocessObject.Object> responseObserver) {
        try {
            TykRequest result = (TykRequest) factory.processRequest(converter.from(request));
            responseObserver.onNext(converter.to(result));
        } catch (BaseMiddlewareException e) {
            responseObserver.onNext(processError(request, 400, e.getMessage()));
        } catch (BaseFactoryException e) {
            responseObserver.onNext(processError(request, 500, e.getMessage()));
        }
        responseObserver.onCompleted();
    }

    private CoprocessObject.Object processError(CoprocessObject.Object request, Integer errorCode, String message) {
        CoprocessObject.Object.Builder errBuilder = request.toBuilder();
        CoprocessReturnOverrides.ReturnOverrides error = CoprocessReturnOverrides.ReturnOverrides.newBuilder().
                setResponseCode(errorCode).setResponseError(message).build();
        errBuilder.getRequestBuilder().setReturnOverrides(error);
        return errBuilder.build();
    }

}