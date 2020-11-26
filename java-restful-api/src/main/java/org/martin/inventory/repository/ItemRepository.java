package org.martin.inventory.repository;

import org.martin.inventory.model.Item;
import org.martin.inventory.model.User;

import javax.persistence.*;
import java.util.List;

public class ItemRepository implements IRepository<Item, Long> {

    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Item> getAll() {
        return entityManager.createQuery("FROM Item", Item.class).getResultList();
    }

    public Item getById(Long id) {
        return entityManager.find(Item.class, id);
    }

    // CRUD
    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item update(Item item) {
        return entityManager.merge(item);
    }

    public void delete(Item item) {
        entityManager.remove(item);
    }
}
