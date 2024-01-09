package com.joelmaciel.productservice.api.controllers;

import com.joelmaciel.productservice.api.dtos.request.ProductRequestDTO;
import com.joelmaciel.productservice.api.dtos.response.ProductResponseDTO;
import com.joelmaciel.productservice.domain.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> getAll(
            @PageableDefault(page = 0, size = 10, sort = "productId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        return productService.findAllProducts(pageable, name);
    }

    @GetMapping("/{productId}")
    public ProductResponseDTO getOne(@PathVariable String productId) {
        return productService.findById(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponseDTO update(
            @PathVariable String productId, @RequestBody @Valid ProductRequestDTO productRequestDTO
    ) {
        return productService.update(productId, productRequestDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO save(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.save(productRequestDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String productId) {
        productService.delete(productId);
    }
}
