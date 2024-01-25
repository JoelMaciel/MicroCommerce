package com.joelmaciel.inventoryservice.domain.services.impl;

import com.joelmaciel.inventoryservice.api.dtos.response.InventoryResponseDTO;
import com.joelmaciel.inventoryservice.domain.repositories.InventoryRepository;
import com.joelmaciel.inventoryservice.domain.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponseDTO> isInStock(List<String> codeSku) {
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait end");
        return inventoryRepository.findByCodeSkuIn(codeSku).stream()
                .map(inventory ->
                        InventoryResponseDTO.builder()
                                .codeSku(inventory.getCodeSku())
                                .inStock(inventory.getQuantity() > 0)
                                .build()
                ).collect(Collectors.toList());
    }
}
