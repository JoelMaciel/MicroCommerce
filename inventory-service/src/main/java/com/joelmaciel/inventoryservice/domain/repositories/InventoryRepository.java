package com.joelmaciel.inventoryservice.domain.repositories;

import com.joelmaciel.inventoryservice.domain.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    List<Inventory> findByCodeSkuIn(List<String> codeSku);
}
