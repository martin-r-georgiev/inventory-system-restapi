package org.martin.inventory.resources;

import org.martin.inventory.model.Item;

public class ItemDTO {

    private Long id;
    private String name;
    private int quantity;

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

    public Item convertToEntity() {
        return new Item(name, quantity);
    }
}

