package com.skywaet.middlewarefactory.factorycommon.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;

@SuperBuilder
@EqualsAndHashCode
@Data
@Table(name = "endpoint_middleware_binding")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class EndpointMiddlewareBinding {

    @EmbeddedId
    private EndpointMiddlewareBindingId id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Phase phase;

    @Column
    @Type(type = "jsonb")
    private Map<String, Object> params;

    @Column
    private Integer place;

    @ManyToOne
    @MapsId("endpointId")
    private Endpoint endpoint;

    @ManyToOne
    @MapsId("middlewareId")
    private Middleware middleware;

}
