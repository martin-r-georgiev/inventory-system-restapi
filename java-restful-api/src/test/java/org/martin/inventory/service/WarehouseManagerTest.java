package org.martin.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martin.inventory.model.Warehouse;
import org.martin.inventory.repository.WarehouseRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseManagerTest {

    //Warehouse Prepared Data
    final String warehouseName = "Test Warehouse";

    @Mock
    private WarehouseRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private WarehouseManager manager;

    @BeforeEach
    private void setup() { lenient().when(manager.entityManager.getTransaction()).thenReturn(transaction); }

    @Test
    void getAllWarehouseTest() {
        //Assign
        List<Warehouse> warehouseList = new ArrayList<Warehouse>();

        //Act
        when(repository.getAll()).thenReturn(warehouseList);
        manager.getAll();
        verify(repository).getAll();
    }

    @Test
    void getWarehouseByIdTest() {
        //Assign
        Warehouse warehouse = new Warehouse(this.warehouseName);
        final UUID whID = warehouse.getId();

        //Act
        when(repository.getById(whID)).thenReturn(warehouse);
        manager.getById(whID);
        verify(repository).getById(whID);
    }

    @Test
    void warehouseExistsTest() {
        //Assign
        Warehouse warehouse = new Warehouse(this.warehouseName);
        final UUID whID = warehouse.getId();

        //Act
        when(repository.exists(whID)).thenReturn(true);
        boolean exists = manager.exists(whID);
        verify(repository).exists(whID);

        //Assert
        assertTrue(exists);
    }

    @Test
    void warehouseCreationTest() {
        //Assign
        Warehouse warehouse = new Warehouse(this.warehouseName);

        //Act
        when(repository.save(warehouse)).thenReturn(warehouse);
        boolean created = manager.add(warehouse);
        verify(repository).save(warehouse);

        //Assert
        assertTrue(created);
    }

    @Test
    void warehouseUpdateTest() {
        //Assign
        Warehouse warehouse = new Warehouse(this.warehouseName);
        final UUID whId = warehouse.getId();

        //Act
        when(repository.getById(whId)).thenReturn(warehouse);
        when(repository.update(warehouse)).thenReturn(warehouse);
        boolean updated = manager.update(whId, warehouse);
        verify(repository).update(warehouse);

        //Assert
        assertTrue(updated);
    }

    @Test
    void warehouseDeleteTest() {
        //Assign
        Warehouse warehouse = new Warehouse(this.warehouseName);

        //Act
        manager.delete(warehouse);
        verify(repository).delete(warehouse);
    }
}
