package com.skywaet.middlewarefactory.grpcserver.repository;

import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.grpcserver.model.EndpointMiddlewareBindingId;
import com.skywaet.middlewarefactory.grpcserver.model.QEndpointMiddlewareBinding;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EndpointMiddlewareRepository extends BaseRepository<EndpointMiddlewareBinding,
        EndpointMiddlewareBindingId,
        QEndpointMiddlewareBinding> {
    @Query(value = "select distinct middleware.name " +
            "from EndpointMiddlewareBinding")
    List<String> getNamesOfAllUsedMiddlewares();

}
