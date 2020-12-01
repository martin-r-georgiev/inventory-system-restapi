package org.martin.inventory.model;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Item() {

    }

    public Item(String name, int quantity) {
        if(! name.isEmpty()) this.name = name;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty name value");
        }
        if (quantity > 0) this.quantity = quantity;
        else {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with a negative quantity value");
        }
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
}
