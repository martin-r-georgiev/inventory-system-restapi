package org.martin.inventory.DTOs;

import org.martin.inventory.model.UserRole;
import org.martin.inventory.model.User;
import org.martin.inventory.model.Warehouse;

import java.util.Objects;
import java.util.UUID;

public class RegistrationDTO {

    // User data
    private String username;
    private String password;

    // Warehouse data
    private UUID warehouseId;
    private String warehouseName;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, String password, UUID warehouseId, String warehouseName) {
        this.username = username;
        this.password = password;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof UserDTO)) return false;

        final RegistrationDTO other = (RegistrationDTO) obj;
        return Objects.equals(username, other.getUsername())
                && Objects.equals(password, other.getPassword())
                && Objects.equals(warehouseId, other.getWarehouseId())
                && Objects.equals(warehouseName, other.getWarehouseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, warehouseId, warehouseName);
    }

    public User GetUserEntity(UserRole role) {
        return new User(username, password, role, warehouseId);
    }

    public Warehouse GetWarehouseEntity() { return new Warehouse(warehouseName); }
}
