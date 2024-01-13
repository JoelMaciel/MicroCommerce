package com.joelmaciel.inventoryservice.api.controllers;

import com.joelmaciel.inventoryservice.api.dtos.response.InventoryResponseDTO;
import com.joelmaciel.inventoryservice.domain.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponseDTO> isInStock(@RequestParam List<String> codeSku) {
        return inventoryService.isInStock(codeSku);
    }
}
