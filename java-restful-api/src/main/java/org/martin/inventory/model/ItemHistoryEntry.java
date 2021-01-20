package org.martin.inventory.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
@SelectBeforeUpdate
public class ItemHistoryEntry {

    // ID, Item ID, Warehouse ID, New Quantity, DateTime of Change

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    @Column(name = "item_id")
    private Long itemId;

    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Warehouse warehouse;

    @Column(name = "warehouse_id")
    @Type(type="uuid-char")
    private UUID warehouseId;

    @NotNull
    private int quantity;

    @NotNull
    private Instant date;

    public ItemHistoryEntry() { }

    public ItemHistoryEntry(Long itemId, UUID warehouseId, int quantity, Instant date) {
        this.itemId = itemId;
        this.warehouseId = warehouseId;
        if (quantity >= 0) this.quantity = quantity;
        else {
            throw new java.lang.IllegalArgumentException("ItemHistoryEntry class object cannot be initialized with a negative quantity value");
        }
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public UUID getWarehouseId() { return warehouseId; }

    public int getQuantity() {  return quantity; }

    public boolean setQuantity(int quantity) {
        if (quantity < 0) return false;
        this.quantity = quantity;
        return true;
    }

    public Instant getDate() { return date; }

    public void setDate(Instant date) {
        this.date = date;
    }
}
