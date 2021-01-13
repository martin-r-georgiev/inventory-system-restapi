package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WarehouseTest {

    //Warehouse Prepared Data
    String name = "Warehouse";

    @Test
    void emptyConstructorTest() {
        //Arrange
        Warehouse warehouse = new Warehouse();

        //Assert
        assertNotNull(warehouse);
    }

    @Test
    void initializationTest() {
        //Arrange
        Warehouse warehouse = new Warehouse(this.name);

        //Assert
        assertEquals(this.name, warehouse.getName());
    }

    @Test
    void initializationNullNameTest() {
        assertThrows(NullPointerException.class, () -> {
            Warehouse warehouse = new Warehouse(null);
        });
    }

    @Test
    void initializationEmptyNameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse("");
        });
    }

    @Test
    void warehouseEmptyNameChangeTest() {
        //Arrange
        Warehouse warehouse = new Warehouse(this.name);

        //Act
        boolean changedValue = warehouse.setName("");

        //Assert
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Warehouse","12134", "Multi-worded Name"})
    void warehouseNameChangeTest(String newName) {
        //Arrange
        Warehouse warehouse = new Warehouse(this.name);

        //Act
        boolean changedValue = warehouse.setName(newName);

        //Assert
        assertEquals(newName, warehouse.getName());
        assertTrue(changedValue);
    }
}
