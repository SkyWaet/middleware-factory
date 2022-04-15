package com.skywaet.middlewarefactory.grpcserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FactoryEndpoint {

    private String method;

    private String apiId;

}
