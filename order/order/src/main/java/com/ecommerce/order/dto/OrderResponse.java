package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;
}
