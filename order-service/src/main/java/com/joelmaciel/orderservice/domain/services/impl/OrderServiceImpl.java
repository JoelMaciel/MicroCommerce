package com.joelmaciel.orderservice.domain.services.impl;

import com.joelmaciel.orderservice.api.config.rabbitmq.Producer;
import com.joelmaciel.orderservice.api.dtos.request.OrderListItemsRequestDTO;
import com.joelmaciel.orderservice.api.dtos.request.OrderRequestDTO;
import com.joelmaciel.orderservice.api.dtos.response.InventoryResponseDTO;
import com.joelmaciel.orderservice.api.event.OrderPlacedEvent;
import com.joelmaciel.orderservice.domain.entitties.Order;
import com.joelmaciel.orderservice.domain.entitties.OrderLineItems;
import com.joelmaciel.orderservice.domain.exceptions.ProductNotFoundException;
import com.joelmaciel.orderservice.domain.repository.OrderRepository;
import com.joelmaciel.orderservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final String STOCK_NOT_FOUND = "There are no products in stock";
    public static final String URL_INVENTORY = "http://inventory-service/api/inventories/";
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final Producer producer;

    @Override
    @Transactional
    public String placeOrder(OrderRequestDTO orderRequestDTO) {
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

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try (Tracer.SpanInScope isLookup = tracer.withSpan(inventoryServiceLookup.start())) {
            inventoryServiceLookup.tag("call", "inventory-service");

            InventoryResponseDTO[] inventoryResponseDTOS = webClientBuilder.build().get()
                    .uri(URL_INVENTORY, uriBuilder -> uriBuilder.queryParam("codeSku", codeSkuList).build())
                    .retrieve()
                    .bodyToMono(InventoryResponseDTO[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponseDTOS)
                    .allMatch(InventoryResponseDTO::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                sendMessage("Order send successfully");
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return "Order completed successfully";

            } else {
                throw new ProductNotFoundException(STOCK_NOT_FOUND);
            }
        } finally {
            inventoryServiceLookup.end();
        }
    }

    private void sendMessage(String message) {
        log.info("Message '{}' send successfully", message);
        producer.send(message);

    }

    private OrderLineItems mapToOrderLineItem(OrderListItemsRequestDTO orderListItemsDTO) {
        return OrderLineItems.builder()
                .codeSku(orderListItemsDTO.getCodeSku())
                .price(orderListItemsDTO.getPrice())
                .quantity(orderListItemsDTO.getQuantity())
                .build();
    }
}
