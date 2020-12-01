package org.martin.inventory.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;

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

    public User() {
    }

    public User(String username, String password) {
        if(! username.isEmpty()) this.username = username;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty name value");
        }
        if(! password.isEmpty()) this.password = password;
        else
        {
            throw new java.lang.IllegalArgumentException("Item class object cannot be initialized with an empty password value");
        }
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
}
