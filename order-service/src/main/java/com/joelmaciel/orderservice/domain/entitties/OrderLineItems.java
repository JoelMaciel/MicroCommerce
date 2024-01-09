package com.joelmaciel.orderservice.domain.entitties;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderLineItems {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;
    private String codeSku;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
