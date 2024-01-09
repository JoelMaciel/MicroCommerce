package com.joelmaciel.orderservice.domain.services.impl;

import com.joelmaciel.orderservice.api.dtos.request.OrderListItemsRequestDTO;
import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;
import com.joelmaciel.orderservice.domain.entitties.Order;
import com.joelmaciel.orderservice.domain.entitties.OrderLineItems;
import com.joelmaciel.orderservice.domain.repository.OrderRepository;
import com.joelmaciel.orderservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber("OrdemNumberTest");

        List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderListItems().stream()
                .map(this::mapToOrderLineItem)
                .peek(item -> item.setOrder(order))
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapToOrderLineItem(OrderListItemsRequestDTO orderListItemsDTO) {
        return OrderLineItems.builder()
                .codeSku(orderListItemsDTO.getCodeSku())
                .price(orderListItemsDTO.getPrice())
                .quantity(orderListItemsDTO.getQuantity())
                .build();
    }
}
