package org.martin.inventory.resources;

import org.martin.inventory.model.Item;
import org.martin.inventory.model.User;

import java.util.Objects;
import java.util.UUID;

public class UserDetails {

    private UUID id;
    private String username;

    public UserDetails() {

    }

    public UserDetails(String username) {
        this.username = username;
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

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
