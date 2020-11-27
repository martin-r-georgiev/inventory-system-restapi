package org.martin.inventory.resources;

import org.martin.inventory.model.User;

import java.util.*;

public class UserDTO {

    private UUID id;
    private String username;
    private String password;

    public UserDTO() {

    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) { this.id = id; }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof UserDTO)) return false;

        final UserDTO other = (UserDTO) obj;
        return Objects.equals(username, other.getUsername()) && Objects.equals(password, other.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public User convertToEntity() {
        return new User(username, password);
    }
}
