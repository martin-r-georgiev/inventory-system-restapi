package org.martin.inventory.service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.UUID;

import org.martin.inventory.model.Item;
import org.martin.inventory.model.Warehouse;
import org.martin.inventory.repository.WarehouseRepository;

public class WarehouseManager {
    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("inventory-persistence");
    EntityManager entityManager = managerFactory.createEntityManager();

    @Inject
    WarehouseRepository repository;

    @PostConstruct
    private void init() {
        repository.setEntityManager(entityManager);
    }

    public List<Warehouse> getAll() {
        return repository.getAll();
    }

    public Warehouse getById(UUID id) { return repository.getById(id); }

    public boolean exists(UUID id) { return repository.exists(id); }

    // CRUD

    public boolean add(Warehouse warehouse) {
        entityManager.getTransaction().begin();
        repository.save(warehouse);
        entityManager.getTransaction().commit();
        entityManager.close();
        return warehouse != null;
    }

    public boolean update(UUID warehouseID, Warehouse warehouse) {
        entityManager.getTransaction().begin();
        Warehouse found = getById(warehouseID);
        if (found != null) {
            found.setName(warehouse.getName());
        }
        Warehouse updated = repository.update(found);
        entityManager.getTransaction().commit();
        entityManager.close();
        return updated != null;
    }

    public void delete(Warehouse warehouse) {
        entityManager.getTransaction().begin();
        repository.delete(warehouse);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Item> getWarehouseItems(String id) {
        return entityManager.createQuery("FROM Item WHERE warehouse_id = :wh_id", Item.class)
                .setParameter("wh_id", id)
                .getResultList();
    }
}
