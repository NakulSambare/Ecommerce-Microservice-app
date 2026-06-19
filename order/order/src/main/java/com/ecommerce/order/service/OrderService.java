package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.OrderStatus;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public OrderResponse createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//        if (userOptional.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//        User user = userOptional.get();
        BigDecimal totaLPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totaLPrice);
        List<OrderItem> orderItems= cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            // set back-reference so Hibernate knows the relationship
            orderItem.setOrder(order);
            return orderItem;
        }).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);
        return mapToOrderResponse(savedOrder);
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setStatus(savedOrder.getStatus());
        orderResponse.setTotalAmount(savedOrder.getTotalAmount());
        savedOrder.getItems().stream().
                map(orderItem -> new OrderItemDto(
                      orderItem.getId(),
                      orderItem.getProductId(),
                      orderItem.getQuantity(),
                        orderItem.getPrice()
                )).toList();
        orderResponse.setCreatedAt(savedOrder.getCreatedAt());
        return orderResponse;
    }
}
