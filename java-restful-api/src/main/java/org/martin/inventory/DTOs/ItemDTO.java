package org.martin.inventory.DTOs;

import org.martin.inventory.model.Item;

import java.util.UUID;

public class ItemDTO {

    private Long id;
    private String name;
    private int quantity;
    private UUID warehouseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean setQuantity(int quantity) {
        if (quantity < 0) return false;
        this.quantity = quantity;
        return true;
    }

    public UUID getWarehouseId() { return warehouseId; }

    public void setWarehouseId(UUID warehouseId) { this.warehouseId = warehouseId; }

    public Item convertToEntity() {
        return new Item(name, quantity, warehouseId);
    }
}

