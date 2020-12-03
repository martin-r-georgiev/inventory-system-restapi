package org.martin.inventory.resources;

import org.martin.inventory.UserRole;
import org.martin.inventory.model.User;

import java.util.*;

public class UserDTO {

    private String username;
    private String password;
    private UUID warehouseId;

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

    public UUID getWarehouseId() { return warehouseId; }

    public void setWarehouseId(UUID warehouseId) { this.warehouseId = warehouseId; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof UserDTO)) return false;

        final UserDTO other = (UserDTO) obj;
        return Objects.equals(username, other.getUsername())
                && Objects.equals(password, other.getPassword())
                && Objects.equals(warehouseId, other.getWarehouseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, warehouseId);
    }

    public User convertToEntity(UserRole role) {
        return new User(username, password, role, warehouseId);
    }
}
