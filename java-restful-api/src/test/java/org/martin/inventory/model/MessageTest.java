package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    //Message Prepared Data
    final String author = "Martin";
    final String content = "Message text";
    final long timestamp = Instant.now().getEpochSecond() * 1000;

    @Test
    void EmptyConstructorTest() {
        //Arrange
        Message message = new Message();

        //Assert
        assertNotNull(message);
    }

    @Test
    void InitializationTest() {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Assert
        assertEquals(this.author, message.getAuthor());
        assertEquals(this.content, message.getContent());
        assertEquals(this.timestamp, message.getTimestamp());
    }

    @Test
    void InitializationNullAuthorTest() {
        assertThrows(NullPointerException.class, () -> {
            Message message = new Message(null, this.content, this.timestamp);
        });
    }

    @Test
    void InitializationNullContentTest() {
        assertThrows(NullPointerException.class, () -> {
            Message message = new Message(this.author, null, this.timestamp);
        });
    }

    @Test
    void InitializationEmptyAuthorTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Message message = new Message("", this.content, this.timestamp);
        });
    }

    @Test
    void InitializationEmptyContentTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Message message = new Message(this.author, "", this.timestamp);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"George","Michael", "John Doe"})
    void MessageAuthorChangeTest(String newAuthor) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setAuthor(newAuthor);

        //Assert
        assertEquals(newAuthor, message.getAuthor());
        assertTrue(changedValue);
    }

    @Test
    void MessageEmptyAuthorChangeTest() {
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
    void MessageContentChangeTest(String newMessageContent) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setContent(newMessageContent);

        //Assert
        assertEquals(newMessageContent, message.getContent());
        assertTrue(changedValue);
    }

    @Test
    void MessageEmptyContentChangeTest() {
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
    void MessageTimestampChangeTest(long newTimestamp) {
        //Arrange
        Message message = new Message(this.author, this.content, this.timestamp);

        //Act
        boolean changedValue = message.setTimestamp(newTimestamp);

        //Assert
        assertEquals(newTimestamp, message.getTimestamp());
        assertTrue(changedValue);
    }

    @Test
    void InvalidMessageTimestampTest() {
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
