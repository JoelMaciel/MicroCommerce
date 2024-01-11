package com.joelmaciel.inventoryservice.domain.services.impl;

import com.joelmaciel.inventoryservice.domain.entities.Inventory;
import com.joelmaciel.inventoryservice.domain.exceptions.InventoryNotFoundException;
import com.joelmaciel.inventoryservice.domain.repositories.InventoryRepository;
import com.joelmaciel.inventoryservice.domain.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String codeSku) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByCodeSku(codeSku);
        return optionalInventory
                .map(inventory -> inventory.getQuantity() > 0)
                .orElseThrow(InventoryNotFoundException::new);
    }
}
