package com.joelmaciel.orderservice.api.controllers;

import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;
import com.joelmaciel.orderservice.domain.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    public static final String ORDER_COMPLETED_SUCCESSFULLY = "Order completed successfully";
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeAnOrder(@RequestBody @Valid OrderRequestDTO orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallBackMethod(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! An error occurred while placing an order.");
    }
}
