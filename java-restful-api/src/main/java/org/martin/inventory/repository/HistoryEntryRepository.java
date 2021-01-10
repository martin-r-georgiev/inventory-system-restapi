package org.martin.inventory.repository;

import org.martin.inventory.model.ItemHistoryEntry;

import javax.persistence.EntityManager;
import java.util.List;

public class HistoryEntryRepository implements IRepository<ItemHistoryEntry, Long>  {

    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ItemHistoryEntry> getAll() {
        return entityManager.createQuery("FROM ItemHistoryEntry", ItemHistoryEntry.class).getResultList();
    }

    public List<ItemHistoryEntry> getAllByItemId(Long itemId)  {
        return entityManager.createQuery("FROM ItemHistoryEntry WHERE item_id = :id", ItemHistoryEntry.class)
                .setParameter("id", itemId)
                .getResultList();
    }

    public List<ItemHistoryEntry> getAllByWarehouseId(String warehouseId)  {
        return entityManager.createQuery("FROM ItemHistoryEntry WHERE warehouse_id = :id", ItemHistoryEntry.class)
                .setParameter("id", warehouseId)
                .getResultList();
    }

    public List<ItemHistoryEntry> getAllByItemAndWarehouseId(Long itemId, String warehouseId)  {
        return entityManager.createQuery("FROM ItemHistoryEntry WHERE item_id = :item_id AND warehouse_id = :wh_id", ItemHistoryEntry.class)
                .setParameter("item_id", itemId)
                .setParameter("wh_id", warehouseId)
                .getResultList();
    }

    public ItemHistoryEntry getById(Long id)  {
        return entityManager.find(ItemHistoryEntry.class, id);
    }

    public ItemHistoryEntry save(ItemHistoryEntry entry) {
        entityManager.persist(entry);
        return entry;
    }

    public ItemHistoryEntry update(ItemHistoryEntry entry) { return entityManager.merge(entry); }

    public void delete(ItemHistoryEntry entry) { entityManager.remove(entry); }

    public void deleteByItemId(Long itemId) {
        entityManager.createQuery("DELETE FROM ItemHistoryEntry WHERE item_id = :id")
                .setParameter("id", itemId);
    }

    public void deleteByWarehouseId(String whId) {
        entityManager.createQuery("DELETE FROM ItemHistoryEntry WHERE warehouse_id = :id")
                .setParameter("id", whId);
    }
}
