package inventory.models;

import java.util.UUID;

public class User {

    private String username;

    private String password;

    private UserRole role;

    private Warehouse warehouse;

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

