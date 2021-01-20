package org.martin.inventory.endpoints;

import org.martin.inventory.DTOs.UserDTO;

import java.util.Objects;
import java.util.UUID;

public class UserDetails {

    private String username;
    private UUID warehouseId;
    private String role;

    public UserDetails() {

    }

    public UserDetails(String username, UUID warehouseId, String role) {
        this.username = username;
        this.warehouseId = warehouseId;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getWarehouseId() { return warehouseId; }

    public void setWarehouseId(UUID warehouseId) { this.warehouseId = warehouseId; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof UserDTO)) return false;

        final UserDetails other = (UserDetails) obj;
        return Objects.equals(username, other.getUsername())
                && Objects.equals(warehouseId, other.getWarehouseId())
                && Objects.equals(role, other.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, warehouseId, role);
    }
}
