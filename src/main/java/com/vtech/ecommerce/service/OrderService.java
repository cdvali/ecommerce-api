package com.vtech.ecommerce.service;

import org.springframework.validation.annotation.Validated;

import com.vtech.ecommerce.dto.OrderDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Validated
public interface OrderService {

    @NotNull Iterable<OrderDTO> getAllOrders();
    
    OrderDTO getOrderById(@NotNull @Min(value = 1L, message = "Invalid order ID.") Long id);

    OrderDTO create(@NotNull(message = "The order cannot be null.") @Valid OrderDTO orderDTO);
    
    void update(Long orderId, OrderDTO orderDTO);
    
    void deleteById(Long id);
}