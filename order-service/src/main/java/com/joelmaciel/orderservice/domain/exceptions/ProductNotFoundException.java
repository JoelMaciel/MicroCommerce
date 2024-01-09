package com.joelmaciel.orderservice.domain.exceptions;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super("Product with given id not found");
    }

}
