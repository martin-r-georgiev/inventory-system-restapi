package org.martin.inventory.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    private String name;

    public Warehouse() { }

    public Warehouse(String name) {
        if (!name.isEmpty()) this.name = name;
        else {
            throw new java.lang.IllegalArgumentException("Warehouse class object cannot be initialized with an empty name value");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.isEmpty()) return false;
        this.name = name;
        return true;
    }
}
