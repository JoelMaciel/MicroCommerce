package com.joelmaciel.orderservice.domain.services;

import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;

public interface OrderService {
    String placeOrder(OrderRequestDTO orderRequestDTO);
}
