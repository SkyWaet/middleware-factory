package com.skywaet.middlewarefactory.factorycommon.model;

import com.skywaet.middlewarefactory.factorycommon.model.base.BaseEntity;
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
@Table(name = "middlewares")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Middleware extends BaseEntity {
    @Column
    private String name;


}
