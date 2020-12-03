package org.martin.inventory.repository;

import org.martin.inventory.model.Warehouse;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class WarehouseRepository implements IRepository<Warehouse, UUID> {
    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Warehouse> getAll() {
        return entityManager.createQuery("FROM Warehouse", Warehouse.class).getResultList();
    }

    public Warehouse getById(UUID id) {
        return entityManager.find(Warehouse.class, id);
    }

    // CRUD
    public Warehouse save(Warehouse warehouse) {
        entityManager.persist(warehouse);
        return warehouse;
    }

    public Warehouse update(Warehouse warehouse) {
        return entityManager.merge(warehouse);
    }

    public void delete(Warehouse warehouse) {
        entityManager.remove(warehouse);
    }
}
