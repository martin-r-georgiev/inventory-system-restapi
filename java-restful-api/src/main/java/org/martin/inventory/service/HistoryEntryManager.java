package org.martin.inventory.service;

import org.hibernate.query.Query;
import org.martin.inventory.DTOs.ItemStockReportDTO;
import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.repository.HistoryEntryRepository;
import org.martin.inventory.DTOs.QuantityReportDTO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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

    @SuppressWarnings("unchecked")
    public List<QuantityReportDTO> getWeeklyQuantityReport(String whId) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -7); // Last 7 days
        Instant pastWeek = c.getTime().toInstant();

        UUID convertedId = UUID.fromString(whId);

        Query<QuantityReportDTO> query = (Query<QuantityReportDTO>) entityManager.createQuery("SELECT NEW org.martin.inventory.DTOs.QuantityReportDTO(Date(ihe.date), SUM(ihe.quantity)) FROM ItemHistoryEntry ihe WHERE ihe.date >= :past AND ihe.warehouseId = :whId GROUP BY DATE(ihe.date)")
                .setParameter("whId", convertedId)
                .setParameter("past", pastWeek);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<ItemStockReportDTO> getItemsInStockReport(String whId) {
        UUID convertedId = UUID.fromString(whId);

        Query<ItemStockReportDTO> query = (Query<ItemStockReportDTO>) entityManager.createQuery("SELECT NEW org.martin.inventory.DTOs.ItemStockReportDTO(SUM(CASE WHEN quantity > 0 THEN 1 ELSE 0 END), SUM(CASE WHEN quantity = 0 THEN 1 ELSE 0 END)) FROM Item WHERE warehouseId = :whId")
                .setParameter("whId", convertedId);
        return query.list();
    }
}
