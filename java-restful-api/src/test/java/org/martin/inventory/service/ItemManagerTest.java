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

@ExtendWith(MockitoExtension.class)
public class ItemManagerTest {

    @Mock
    private ItemRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private ItemManager manager = new ItemManager();

    @BeforeEach
    private void setup() {
        lenient().when(manager.entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void AccountCreationTest() {
        //Assign
        Item item = new Item("Item", 250);

        //Act
        when(repository.save(item)).thenReturn(item);
        boolean created = manager.add(item);
        verify(repository).save(item);

        //Assert
        assertTrue(created);
    }

    @Test
    void AccountUpdateTest() {
        //Assign
        Item item = new Item("Item", 250);
        Long itemId = 5L;

        //Act
        when(repository.getById(itemId)).thenReturn(item);
        when(repository.update(item)).thenReturn(item);
        boolean updated = manager.update(itemId, item);
        verify(repository).update(item);

        //Assert
        assertTrue(updated);
    }

}
