package com.skywaet.middlewarefactory.grpcserver.repository;

import com.querydsl.core.types.EntityPath;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable, Q extends EntityPath<T>> extends JpaRepository<T, I>, QuerydslPredicateExecutor<T>,
        QuerydslBinderCustomizer<Q> {

    default void customize(QuerydslBindings bindings, Q entity) {
    }
}
