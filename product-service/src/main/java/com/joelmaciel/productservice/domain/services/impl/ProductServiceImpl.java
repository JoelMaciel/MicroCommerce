package com.joelmaciel.productservice.domain.services.impl;

import com.joelmaciel.productservice.api.dtos.request.ProductRequestDTO;
import com.joelmaciel.productservice.api.dtos.response.ProductResponseDTO;
import com.joelmaciel.productservice.domain.entities.Product;
import com.joelmaciel.productservice.domain.exceptions.ProductNotFoundException;
import com.joelmaciel.productservice.domain.repositories.ProductRepository;
import com.joelmaciel.productservice.domain.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllProducts(Pageable pageable, String name) {
        Page<Product> products = getProductPage(pageable, name);
        return products.map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(String productId) {
        Product product = findProductId(productId);
        return toDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(String productId, ProductRequestDTO productRequestDTO) {
        Product product = findProductId(productId);
        Product updatedProduct = updateProductFields(product, productRequestDTO);
        return toDTO(productRepository.save(updatedProduct));
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = toEntity(productRequestDTO);
        log.info("Product to be saved", product.getName(), product.getDescription());
        return toDTO(productRepository.save(product));

    }

    @Override
    @Transactional
    public void delete(String productId) {
        Product product = findProductId(productId);
        productRepository.delete(product);
    }
    @Override
    @Transactional(readOnly = true)
    public Product findProductId(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private Page<Product> getProductPage(Pageable pageable, String name) {
        Page<Product> products;
        if (name != null) {
            products = productRepository.findByNameContaining(pageable, name);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products;
    }

    private Product updateProductFields(Product product, ProductRequestDTO productRequestDTO) {
        return product.toBuilder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .build();
    }


    private Product toEntity(ProductRequestDTO requestDTO) {
        return Product.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .build();
    }

    private ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
