package org.martin.inventory.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.martin.inventory.model.Warehouse;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WarehouseTest {
    //Warehouse Prepared Data
    String name = "Warehouse";

    @Test
    void EmptyConstructorTest() {
        //Arrange
        Warehouse warehouse = new Warehouse();

        //Assert
        assertNotNull(warehouse);
    }

    @Test
    void InitializationTest() {
        //Arrange
        Warehouse warehouse = new Warehouse(this.name);

        //Assert
        assertEquals(this.name, warehouse.getName());
    }

    @Test
    void InitializationNullNameTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Warehouse warehouse = new Warehouse(null);
        });
    }

    @Test
    void InitializationEmptyNameTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse("");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Warehouse","12134", "Multi-worded Name"})
    void WarehouseNameChangeTest(String newName) {
        //Arrange
        Warehouse warehouse = new Warehouse(this.name);

        //Act
        boolean changedValue = warehouse.setName(newName);

        //Assert
        assertEquals(newName, warehouse.getName());
        assertTrue(changedValue);
    }
}
