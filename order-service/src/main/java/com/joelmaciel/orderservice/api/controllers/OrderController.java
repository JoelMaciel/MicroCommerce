package com.joelmaciel.orderservice.api.controllers;

import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;
import com.joelmaciel.orderservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    public static final String ORDER_COMPLETED_SUCCESSFULLY = "Order completed successfully";
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeAnOrder(@RequestBody @Valid OrderRequestDTO orderRequest) {
        orderService.placeOrder(orderRequest);
        return ORDER_COMPLETED_SUCCESSFULLY;
    }
}
