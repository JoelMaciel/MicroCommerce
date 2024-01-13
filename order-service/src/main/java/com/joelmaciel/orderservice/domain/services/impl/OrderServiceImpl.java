package com.joelmaciel.orderservice.domain.services.impl;

import com.joelmaciel.orderservice.api.dtos.request.OrderListItemsRequestDTO;
import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;
import com.joelmaciel.orderservice.api.dtos.response.InventoryResponseDTO;
import com.joelmaciel.orderservice.domain.entitties.Order;
import com.joelmaciel.orderservice.domain.entitties.OrderLineItems;
import com.joelmaciel.orderservice.domain.exceptions.ProductNotFoundException;
import com.joelmaciel.orderservice.domain.repository.OrderRepository;
import com.joelmaciel.orderservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final String STOCK_NOT_FOUND = "There are no products in stock";
    public static final String URL_INVENTORY = "http://localhost:8082/api/inventories/";
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    @Transactional
    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderListItems().stream()
                .map(this::mapToOrderLineItem)
                .peek(item -> item.setOrder(order))
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> codeSkuList = order.getOrderLineItems().stream()
                .map(OrderLineItems::getCodeSku)
                .collect(Collectors.toList());

        InventoryResponseDTO[] inventoryResponseDTOS = webClient.get()
                .uri(URL_INVENTORY, uriBuilder -> uriBuilder.queryParam("codeSku", codeSkuList).build())
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseDTOS)
                .allMatch(InventoryResponseDTO::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);

        } else {
            throw new ProductNotFoundException(STOCK_NOT_FOUND);
        }
    }

    private OrderLineItems mapToOrderLineItem(OrderListItemsRequestDTO orderListItemsDTO) {
        return OrderLineItems.builder()
                .codeSku(orderListItemsDTO.getCodeSku())
                .price(orderListItemsDTO.getPrice())
                .quantity(orderListItemsDTO.getQuantity())
                .build();
    }
}
