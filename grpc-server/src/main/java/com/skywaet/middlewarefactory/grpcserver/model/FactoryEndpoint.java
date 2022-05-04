package com.skywaet.middlewarefactory.grpcserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryEndpoint {

    private String method;

    private String apiId;

    private String uri;

}
