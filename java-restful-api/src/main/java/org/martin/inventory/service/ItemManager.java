package org.martin.inventory.service;

import org.martin.inventory.model.Item;
import org.martin.inventory.repository.ItemRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ItemManager {
    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("inventory-persistence");
    EntityManager entityManager = managerFactory.createEntityManager();

    @Inject
    ItemRepository repository;

    @PostConstruct
    private void init() {
        repository.setEntityManager(entityManager);
    }

    public List<Item> getAll() {
        return repository.getAll();
    }

    public Item getById(Long id) {
        return repository.getById(id);
    }

    // CRUD

    public boolean add(Item item) {
        entityManager.getTransaction().begin();
        repository.save(item);
        entityManager.getTransaction().commit();
        entityManager.close();
        return item != null;
    }

    public boolean update(Long itemId, Item item) {
        entityManager.getTransaction().begin();
        Item found = getById(itemId);
        if (found != null) {
            found.setName(item.getName());
            found.setQuantity(item.getQuantity());
        }
        Item updated = repository.update(found);
        entityManager.getTransaction().commit();
        entityManager.close();
        return updated != null;
    }

    public void delete(Item item) {
        entityManager.getTransaction().begin();
        repository.delete(item);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
