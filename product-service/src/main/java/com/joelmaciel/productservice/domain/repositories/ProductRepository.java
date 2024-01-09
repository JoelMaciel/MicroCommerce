package com.joelmaciel.productservice.domain.repositories;

import com.joelmaciel.productservice.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository  extends MongoRepository<Product, String> {
    Page<Product> findByNameContaining(Pageable pageable, String name);
}
