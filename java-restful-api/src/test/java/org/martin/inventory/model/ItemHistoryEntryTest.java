package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ItemHistoryEntryTest {

    //Item Prepared Data
    private final Long itemId = 1234L;
    private final UUID whId = UUID.randomUUID();
    private final int quantity = 100;
    private final Instant date = Instant.now();

    @Test
    void emptyConstructorTest() {
        //Arrange
        ItemHistoryEntry entry = new ItemHistoryEntry();

        //Assert
        assertNotNull(entry);
    }

    @Test
    void initializationTest() {
        //Arrange
        ItemHistoryEntry entry = new ItemHistoryEntry(this.itemId, this.whId, this.quantity, this.date);

        //Assert
        assertEquals(this.itemId, entry.getItemId());
        assertEquals(this.whId, entry.getWarehouseId());
        assertEquals(this.quantity, entry.getQuantity());
        assertEquals(this.date, entry.getDate());
    }

    @Test
    void initializationInvalidQuantityTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            ItemHistoryEntry entry = new ItemHistoryEntry(this.itemId, this.whId, -123, this.date);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 1400})
    void entryQuantityChangeTest(int newQuantity) {
        //Arrange
        ItemHistoryEntry entry = new ItemHistoryEntry(this.itemId, this.whId, this.quantity, this.date);

        //Act
        boolean changedValue = entry.setQuantity(newQuantity);

        //Assert
        assertEquals(newQuantity, entry.getQuantity());
        assertTrue(changedValue);
    }

    @Test
    void invalidEntryQuantityChangeTest() {
        //Arrange
        ItemHistoryEntry entry = new ItemHistoryEntry(this.itemId, this.whId, this.quantity, this.date);
        final int newQuantity = -1;

        //Act
        boolean changedValue = entry.setQuantity(newQuantity);

        //Assert
        assertEquals(this.quantity, entry.getQuantity());
        assertFalse(changedValue);
    }

    @Test
    void entryDateChangeTest() {
        //Arrange
        ItemHistoryEntry entry = new ItemHistoryEntry(this.itemId, this.whId, this.quantity, this.date);
        final Instant newDate = Instant.now();

        //Act
        entry.setDate(newDate);

        //Assert
        assertEquals(newDate, entry.getDate());
    }
}
