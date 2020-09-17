package org.martin.inventory.repository;

import org.martin.inventory.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    private final List<Item> itemList = new ArrayList<>();

    public ItemRepository() {
        // Temporary information storage
        //TODO: Transfer to a (MySQL) database

        Item item1 = new Item("Laptop", 50);
        Item item2 = new Item("Phone", 225);
        Item item3 = new Item("Refrigerator", 20);
        Item item4 = new Item("Router", 15);

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
    }

    public List<Item> getItems() {
        return itemList;
    }

    public Item getItem(int id) {
        for (Item item : itemList) {
            if(item.getId() == id) return item;
        }
        return null;
    }

    // CRUD

    public boolean addItem(Item item) {
        if (this.getItem(item.getId()) != null) return false;
        itemList.add(item);
        return true;
    }

    public boolean updateItem(Item item) {
        Item oldItem = this.getItem(item.getId());
        if (oldItem == null) return false;

        oldItem.setName(item.getName());
        oldItem.setQuantity(item.getQuantity());
        return true;
    }

    public boolean deleteItem(Item item) {
        Item found = this.getItem(item.getId());
        if (found == null) return false;

        return itemList.remove(found);
    }
}
