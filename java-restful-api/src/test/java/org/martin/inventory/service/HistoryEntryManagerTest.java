package org.martin.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.repository.HistoryEntryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryEntryManagerTest {

    @Mock
    private HistoryEntryRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private HistoryEntryManager manager;

    @BeforeEach
    private void setup() { lenient().when(manager.entityManager.getTransaction()).thenReturn(transaction); }

    @Test
    void getAllEntriesTest() {
        //Assign
        List<ItemHistoryEntry> entryList = new ArrayList<>();

        //Act
        when(repository.getAll()).thenReturn(entryList);
        manager.getAll();
        verify(repository).getAll();
    }

    @Test
    void getEntryByIdTest() {
        //Assign
        ItemHistoryEntry entry = new ItemHistoryEntry(123L, UUID.randomUUID(), 10, Instant.now());
        final Long entryId = entry.getId();

        //Act
        when(repository.getById(entryId)).thenReturn(entry);
        manager.getById(entryId);
        verify(repository).getById(entryId);
    }

    @Test
    void getEntriesByItemIdTest() {
        //Assign
        List<ItemHistoryEntry> entryList = new ArrayList<>();
        final Long itemId = 123L;

        //Act
        when(repository.getAllByItemId(itemId)).thenReturn(entryList);
        manager.getAllByItemId(itemId);
        verify(repository).getAllByItemId(itemId);
    }

    @Test
    void getEntriesByWarehouseIdTest() {
        //Assign
        List<ItemHistoryEntry> entryList = new ArrayList<>();
        final String whId = UUID.randomUUID().toString();

        //Act
        when(repository.getAllByWarehouseId(whId)).thenReturn(entryList);
        manager.getAllByWarehouseId(whId);
        verify(repository).getAllByWarehouseId(whId);
    }

    @Test
    void getEntriesByItemAndWarehouseIdTest() {
        //Assign
        List<ItemHistoryEntry> entryList = new ArrayList<>();
        final Long itemId = 123L;
        final String whId = UUID.randomUUID().toString();

        //Act
        when(repository.getAllByItemAndWarehouseId(itemId, whId)).thenReturn(entryList);
        manager.getAllByItemAndWarehouseId(itemId, whId);
        verify(repository).getAllByItemAndWarehouseId(itemId, whId);
    }

    @Test
    void historyEntryCreationTest() {
        //Assign
        ItemHistoryEntry entry = new ItemHistoryEntry(123L, UUID.randomUUID(), 10, Instant.now());

        //Act
        when(repository.save(entry)).thenReturn(entry);
        boolean created = manager.add(entry);
        verify(repository).save(entry);

        //Assert
        assertTrue(created);
    }

    @Test
    void historyEntryUpdateTest() {
        //Assign
        ItemHistoryEntry entry = new ItemHistoryEntry(123L, UUID.randomUUID(), 10, Instant.now());
        final Long entryId = entry.getId();

        //Act
        when(repository.getById(entryId)).thenReturn(entry);
        when(repository.update(entry)).thenReturn(entry);
        boolean updated = manager.update(entryId, entry);
        verify(repository).update(entry);

        //Assert
        assertTrue(updated);
    }

    @Test
    void historyEntryDeleteTest() {
        //Assign
        ItemHistoryEntry entry = new ItemHistoryEntry(123L, UUID.randomUUID(), 10, Instant.now());

        //Act
        manager.delete(entry);
        verify(repository).delete(entry);
    }

    @Test
    void historyEntryDeleteByItemIdTest() {
        //Assign
        final Long itemId = 123L;

        //Act
        manager.deleteByItemId(itemId);
        verify(repository).deleteByItemId(itemId);
    }

    @Test
    void historyEntryDeleteByWarehouseIdTest() {
        //Assign
        final String whId = UUID.randomUUID().toString();

        //Act
        manager.deleteByWarehouseId(whId);
        verify(repository).deleteByWarehouseId(whId);
    }
}
