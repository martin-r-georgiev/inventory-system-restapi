package org.martin.inventory.resources;

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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof UserDTO)) return false;

        final UserDetails other = (UserDetails) obj;
        return Objects.equals(username, other.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
