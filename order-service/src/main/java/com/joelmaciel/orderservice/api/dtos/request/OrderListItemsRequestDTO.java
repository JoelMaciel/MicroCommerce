package com.joelmaciel.orderservice.api.dtos.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemsRequestDTO {

    @NotBlank
    private String codeSku;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    @NotNull
    private Integer quantity;
}
