package com.skywaet.middlewarefactory.grpcserver.repository;


import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.factorycommon.model.EndpointMiddlewareBindingId;
import com.skywaet.middlewarefactory.factorycommon.model.QEndpointMiddlewareBinding;
import com.skywaet.middlewarefactory.factorycommon.repository.BaseRepository;
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
