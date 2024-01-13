package com.joelmaciel.inventoryservice.domain.exceptions;

public abstract class ResourceNotFoundException extends BusinessException {

    ResourceNotFoundException(String message) {
        super(message);
    }
}
