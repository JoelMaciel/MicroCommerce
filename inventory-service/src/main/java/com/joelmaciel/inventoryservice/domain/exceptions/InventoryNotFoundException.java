package com.joelmaciel.inventoryservice.domain.exceptions;

public class InventoryNotFoundException extends ResourceNotFoundException {
    public InventoryNotFoundException() {
        super("There is no product with this code or stock is empty");
    }

}
