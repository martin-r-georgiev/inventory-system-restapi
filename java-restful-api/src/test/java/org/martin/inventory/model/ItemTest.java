package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class ItemTest {

    //Item Prepared Data
    String name = "Item";
    int quantity = 100;
    int invalidQuantity = -10;
    UUID whId = UUID.randomUUID();

    @Test
    void emptyConstructorTest() {
        //Arrange
        Item item = new Item();

        //Assert
        assertNotNull(item);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 99, 100})
    void initializationTest(int quantity) {
        //Arrange
        Item item = new Item(this.name, quantity, this.whId);

        //Assert
        assertEquals(this.name, item.getName());
        assertEquals(quantity, item.getQuantity());
    }

    @Test
    void initializationNullNameTest() {
        assertThrows(NullPointerException.class, () -> {
            Item item = new Item(null, this.quantity, this.whId);
        });
    }

    @Test
    void initializationEmptyNameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item("", this.quantity, this.whId);
        });
    }

    @Test
    void initializationInvalidQuantityTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item(this.name, this.invalidQuantity, this.whId);
        });
    }

    @Test
    void itemNameChangeTest() {
        //Arrange
        Item item = new Item(this.name, this.quantity, this.whId);
        final String newName = "NewItemName";

        //Act
        item.setName(newName);

        //Assert
        assertEquals(newName, item.getName());
    }

    @Test
    void itemNullNameChangeTest() {
        assertThrows(NullPointerException.class, () -> {
            Item item = new Item(this.name, this.quantity, this.whId);
            item.setName(null);
        });
    }

    @Test
    void itemEmptyNameChangeTest() {
        //Arrange
        Item item = new Item(this.name, this.quantity, this.whId);

        //Act
        boolean changedValue = item.setName("");

        //Assert
        assertEquals(this.name, item.getName());
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 530, 33240})
    void itemQuantityChangeTest(int newQuantity) {
        //Arrange
        Item item = new Item(this.name, this.quantity, this.whId);

        //Act
        boolean changedValue = item.setQuantity(newQuantity);

        //Assert
        assertEquals(newQuantity, item.getQuantity());
        assertTrue(changedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -3430})
    void itemQuantityOutOfRangeTest(int newQuantity) {
        //Arrange
        Item item = new Item(this.name, this.quantity, this.whId);

        //Act
        boolean changedValue = item.setQuantity(newQuantity);

        //Assert
        assertEquals(this.quantity, item.getQuantity());
        assertFalse(changedValue);
    }
}
