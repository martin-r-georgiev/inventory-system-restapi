package org.martin.inventory.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martin.inventory.UserRole;
import org.martin.inventory.model.Item;
import org.martin.inventory.model.User;
import org.martin.inventory.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ItemManagerTest {
    UUID whId = UUID.randomUUID();

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
    void ItemCreationTest() {
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
    void ItemUpdateTest() {
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
    void UserDeleteTest() {
        //Assign
        Item item = new Item("Item", 250, this.whId);

        //Act
        manager.delete(item);
        verify(repository).delete(item);
    }
}
