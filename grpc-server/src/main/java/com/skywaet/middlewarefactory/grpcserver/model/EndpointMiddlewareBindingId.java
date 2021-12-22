package com.skywaet.middlewarefactory.grpcserver.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class EndpointMiddlewareBindingId implements Serializable {

    private Long middlewareId;
    private Long endpointId;

}
