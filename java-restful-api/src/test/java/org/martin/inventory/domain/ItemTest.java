package org.martin.inventory.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import org.martin.inventory.model.Item;

public class ItemTest {

    //Item Prepared Data
    String name = "Item";
    int quantity = 100;
    int invalidQuantity = -10;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 99, 100})
    void InitializationTest() {
        //Arrange
        Item item = new Item(this.name, this.quantity);

        //Assert
        assertEquals(this.name, item.getName());
        assertEquals(this.quantity, item.getQuantity());
    }

    @Test
    void InitializationNullNameTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Item item = new Item(null, this.quantity);
        });
    }

    @Test
    void InitializationEmptyNameTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item("", this.quantity);
        });
    }

    @Test
    void InitializationInvalidQuantityTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item(this.name, this.invalidQuantity);
        });
    }

    @Test
    void ItemNameChangeTest() {
        //Arrange
        Item item = new Item(this.name, this.quantity);
        final String newName = "NewItemName";

        //Act
        item.setName(newName);

        //Assert
        assertEquals(newName, item.getName());
    }

    @Test
    void ItemNullNameChangeTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Item item = new Item(this.name, this.quantity);
            item.setName(null);
        });
    }

    @Test
    void ItemEmptyNameChangeTest() {
        //Arrange
        Item item = new Item(this.name, this.quantity);

        //Act
        boolean changedValue = item.setName("");

        //Assert
        assertEquals(this.name, item.getName());
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 530, 33240})
    void ItemQuantityChangeTest(int newQuantity) {
        //Arrange
        Item item = new Item(this.name, this.quantity);

        //Act
        boolean changedValue = item.setQuantity(newQuantity);

        //Assert
        assertEquals(newQuantity, item.getQuantity());
        assertTrue(changedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -3430})
    void ItemQuantityOutOfRangeTest(int newQuantity) {
        //Arrange
        Item item = new Item(this.name, this.quantity);

        //Act
        boolean changedValue = item.setQuantity(newQuantity);

        //Assert
        assertEquals(this.quantity, item.getQuantity());
        assertFalse(changedValue);
    }
}
