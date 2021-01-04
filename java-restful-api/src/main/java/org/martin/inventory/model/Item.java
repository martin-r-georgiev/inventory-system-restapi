package org.martin.inventory.model;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@SelectBeforeUpdate
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @Column(name = "warehouse_id")
    @Type(type="uuid-char")
    private UUID warehouseId;

    public Item() {

    }

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
