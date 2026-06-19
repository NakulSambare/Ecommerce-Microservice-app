package com.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;



}
