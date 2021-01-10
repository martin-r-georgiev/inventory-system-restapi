package org.martin.inventory.service;

import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.repository.HistoryEntryRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HistoryEntryManager {
    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("inventory-persistence");
    EntityManager entityManager = managerFactory.createEntityManager();

    @Inject
    HistoryEntryRepository repository;

    @PostConstruct
    private void init() {
        repository.setEntityManager(entityManager);
    }

    public List<ItemHistoryEntry> getAll() {
        return repository.getAll();
    }

    public List<ItemHistoryEntry> getAllByItemId(Long itemId) { return repository.getAllByItemId(itemId); }

    public List<ItemHistoryEntry> getAllByWarehouseId(String whId) { return repository.getAllByWarehouseId(whId); }

    public List<ItemHistoryEntry> getAllByItemAndWarehouseId(Long itemId, String whId) {
        return repository.getAllByItemAndWarehouseId(itemId, whId);
    }

    public ItemHistoryEntry getById(Long id) { return repository.getById(id); }

    // CRUD

    public boolean add(ItemHistoryEntry entry) {
        entityManager.getTransaction().begin();
        repository.save(entry);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entry != null;
    }

    public boolean update(Long id, ItemHistoryEntry entry) {
        entityManager.getTransaction().begin();
        ItemHistoryEntry found = getById(id);
        if (found != null) {
            found.setQuantity(entry.getQuantity());
            found.setDate(entry.getDate());
        }
        ItemHistoryEntry updated = repository.update(found);
        entityManager.getTransaction().commit();
        entityManager.close();
        return updated != null;
    }

    public void delete(ItemHistoryEntry entry) {
        entityManager.getTransaction().begin();
        repository.delete(entry);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteByItemId(Long itemId) { repository.deleteByItemId(itemId); }

    public void deleteByWarehouseId(String whId) { repository.deleteByWarehouseId(whId); }
}
