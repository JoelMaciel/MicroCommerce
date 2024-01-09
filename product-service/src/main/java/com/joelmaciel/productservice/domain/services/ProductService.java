package com.joelmaciel.productservice.domain.services;

import com.joelmaciel.productservice.api.dtos.request.ProductRequestDTO;
import com.joelmaciel.productservice.api.dtos.response.ProductResponseDTO;
import com.joelmaciel.productservice.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    ProductResponseDTO save(ProductRequestDTO productRequestDTO);

    Page<ProductResponseDTO> findAllProducts(Pageable pageable, String name);

    ProductResponseDTO findById(String productId);

    Product findProductId(String productId);

    ProductResponseDTO update(String productId, ProductRequestDTO productRequestDTO);

    void delete(String productId);
}
