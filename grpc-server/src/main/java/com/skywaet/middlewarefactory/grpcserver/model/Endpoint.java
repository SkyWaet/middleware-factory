package com.skywaet.middlewarefactory.grpcserver.model;

import com.skywaet.middlewarefactory.grpcserver.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "endpoints")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Endpoint extends BaseEntity {
    @Column
    private String uri;

    @Column
    private String method;

    @Column
    private Long apiId;
}
