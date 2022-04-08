package  com.skywaet.middlewarefactory.factorycommon.model;

import com.skywaet.middlewarefactory.factorycommon.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

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
    private String apiId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "endpoint")
    private List<EndpointMiddlewareBinding> middlewares;
}
