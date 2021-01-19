package org.martin.inventory.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martin.inventory.model.Item;
import org.martin.inventory.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ItemManagerTest {
    private final UUID whId = UUID.randomUUID();

    @Mock
    private ItemRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private ItemManager manager;

    @BeforeEach
    private void setup() {
        lenient().when(manager.entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void getAllItemsTest() {
        //Assign
        List<Item> itemList = new ArrayList<>();

        //Act
        when(repository.getAll()).thenReturn(itemList);
        manager.getAll();
        verify(repository).getAll();
    }

    @Test
    void getItemByIdTest() {
        //Assign
        Item item = new Item("Item", 250, this.whId);
        final Long itemId = item.getId();

        //Act
        when(repository.getById(itemId)).thenReturn(item);
        manager.getById(itemId);
        verify(repository).getById(itemId);
    }

    @Test
    void itemCreationTest() {
        //Assign
        Item item = new Item("Item", 250, this.whId);

        //Act
        when(repository.save(item)).thenReturn(item);
        boolean created = manager.add(item);
        verify(repository).save(item);

        //Assert
        assertTrue(created);
    }

    @Test
    void itemUpdateTest() {
        //Assign
        Item item = new Item("Item", 250, this.whId);
        Long itemId = 5L;

        //Act
        when(repository.getById(itemId)).thenReturn(item);
        when(repository.update(item)).thenReturn(item);
        Item updated = manager.update(itemId, item);
        verify(repository).update(item);

        //Assert
        assertNotNull(updated);
    }

    @Test
    void itemDeleteTest() {
        //Assign
        Item item = new Item("Item", 250, this.whId);

        //Act
        manager.delete(item);
        verify(repository).delete(item);
    }
}
