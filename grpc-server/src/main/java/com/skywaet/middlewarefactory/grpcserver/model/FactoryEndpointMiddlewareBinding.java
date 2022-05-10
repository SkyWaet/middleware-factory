package com.skywaet.middlewarefactory.grpcserver.model;

import com.skywaet.middlewarefactory.factorycommon.model.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryEndpointMiddlewareBinding implements Comparable<FactoryEndpointMiddlewareBinding> {

    private String middlewareName;

    private Integer place;

    private Phase phase;

    private Object params;

    @Override
    public int compareTo(FactoryEndpointMiddlewareBinding o) {
        return this.place - o.place;
    }
}
