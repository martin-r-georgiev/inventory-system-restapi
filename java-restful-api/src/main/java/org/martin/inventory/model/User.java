package org.martin.inventory.model;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@SelectBeforeUpdate
public class User {

    @Id
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private UserRole role;

    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @Column(name = "warehouse_id")
    @Type(type="uuid-char")
    private UUID warehouseId;

    public User() { }

    public User(String username, String password, UserRole role, UUID warehouseId) {
        if(!username.isEmpty()) this.username = username;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty name value");
        }
        if(!password.isEmpty()) this.password = password;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty password value");
        }
        this.role = role;
        this.warehouseId = warehouseId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (password.isEmpty()) return false;
        this.password = password;
        return true;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UUID getWarehouseId() { return warehouseId; }

    public void setWarehouseId(UUID warehouseId) { this.warehouseId = warehouseId; }
}
