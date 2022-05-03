package com.skywaet.middlewarefactory.restapi.repository;

import com.skywaet.middlewarefactory.factorycommon.model.Middleware;
import com.skywaet.middlewarefactory.factorycommon.model.QMiddleware;
import com.skywaet.middlewarefactory.factorycommon.repository.BaseRepository;

import java.util.Optional;

public interface MiddlewareRepository extends BaseRepository<Middleware, Long, QMiddleware> {
    Optional<Middleware> getMiddlewareByName(String name);
}
