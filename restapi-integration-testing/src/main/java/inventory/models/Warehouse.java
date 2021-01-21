package inventory.models;

import java.util.UUID;

public class Warehouse {

    private UUID id;

    private String name;

    public Warehouse() { }

    public Warehouse(String name) {
        if (!name.isEmpty()) this.name = name;
        else {
            throw new java.lang.IllegalArgumentException("Warehouse class object cannot be initialized with an empty name value");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.isEmpty()) return false;
        this.name = name;
        return true;
    }
}
