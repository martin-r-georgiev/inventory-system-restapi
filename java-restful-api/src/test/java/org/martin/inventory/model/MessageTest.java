package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    //Message Prepared Data
    private final String author = "Martin";
    private final String content = "Message text";
    private final long timestamp = Instant.now().getEpochSecond() * 1000;

    @Test
    void emptyConstructorTest() {
        //Arrange
        Message message = new Message();

        //Assert
        assertNotNull(message);
    }

    @Test
    void initializationTest() {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Assert
        assertEquals(this.author, message.getAuthor());
        assertEquals(this.content, message.getContent());
        assertEquals(this.timestamp, message.getTimestamp());
    }

    @Test
    void initializationNullAuthorTest() {
        assertThrows(NullPointerException.class, () -> {
            Message message = new Message(null, this.content, this.timestamp);
        });
    }

    @Test
    void initializationNullContentTest() {
        assertThrows(NullPointerException.class, () -> {
            Message message = new Message(this.author, null, this.timestamp);
        });
    }

    @Test
    void initializationEmptyAuthorTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Message message = new Message("", this.content, this.timestamp);
        });
    }

    @Test
    void initializationEmptyContentTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Message message = new Message(this.author, "", this.timestamp);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"George","Michael", "John Doe"})
    void messageAuthorChangeTest(String newAuthor) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setAuthor(newAuthor);

        //Assert
        assertEquals(newAuthor, message.getAuthor());
        assertTrue(changedValue);
    }

    @Test
    void messageEmptyAuthorChangeTest() {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setAuthor("");

        //Assert
        assertEquals(this.author, message.getAuthor());
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Warehouse","12134", "Multi-worded Name"})
    void messageContentChangeTest(String newMessageContent) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setContent(newMessageContent);

        //Assert
        assertEquals(newMessageContent, message.getContent());
        assertTrue(changedValue);
    }

    @Test
    void messageEmptyContentChangeTest() {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setContent("");

        //Assert
        assertEquals(this.content, message.getContent());
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {34546, 235034503, 123257678})
    void messageTimestampChangeTest(long newTimestamp) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setTimestamp(newTimestamp);

        //Assert
        assertEquals(newTimestamp, message.getTimestamp());
        assertTrue(changedValue);
    }

    @Test
    void invalidMessageTimestampTest() {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);
        final long newTimestamp = -4;

        //Act
        boolean changedValue = message.setTimestamp(newTimestamp);

        //Assert
        assertEquals(this.timestamp, message.getTimestamp());
        assertFalse(changedValue);
    }
}
