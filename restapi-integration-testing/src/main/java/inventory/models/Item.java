package inventory.models;

import java.util.UUID;

public class Item {

    private Long id;

    private String name;

    private int quantity;

    private Warehouse warehouse;

    private UUID warehouseId;

    public Item() { }

    public Item(String name, int quantity, UUID warehouseId) {
        if(! name.isEmpty()) this.name = name;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty name value");
        }
        if (quantity >= 0) this.quantity = quantity;
        else {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with a negative quantity value");
        }
        this.warehouseId = warehouseId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name)
    {
        if (name.isEmpty()) return false;
        this.name = name;
        return true;
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
}

