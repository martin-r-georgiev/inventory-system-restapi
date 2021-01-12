package org.martin.inventory.model;

public enum UserRole {
    User(0), Manager(1), Admin(2);

    private int value;

    private UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
