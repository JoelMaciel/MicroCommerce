package com.joelmaciel.inventoryservice.domain.services;

import com.joelmaciel.inventoryservice.api.dtos.response.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryResponseDTO> isInStock(List<String> codeSku);
}
