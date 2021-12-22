package com.skywaet.middlewarefactory.grpcserver.repository;

import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBindingId;
import com.skywaet.middlewarefactory.grpcserver.model.QEndpointMiddlewareBinding;
import org.springframework.stereotype.Repository;


@Repository
public interface EndpointMiddlewareRepository extends BaseRepository<EndpointMiddlewareBinding,
        EndpointMiddlewareBindingId,
        QEndpointMiddlewareBinding> {
}
