package com.skywaet.middlewarefactory.grpcserver.model;

import com.skywaet.middlewarefactory.grpcserver.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "middlewares")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Middleware extends BaseEntity {
    @Column
    private String name;

    @Column
    @Enumerated(value = EnumType.STRING)
    private LocalHookType type;


}
