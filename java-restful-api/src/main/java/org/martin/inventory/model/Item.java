package org.martin.inventory.model;

public class Item {
    private static int autoIncrement = 0;

    private int id;
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.id = autoIncrement;
        this.name = name;
        this.quantity = quantity;

        autoIncrement++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
