package com.joelmaciel.orderservice.domain.repository;

import com.joelmaciel.orderservice.domain.entitties.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
