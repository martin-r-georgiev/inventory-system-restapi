package org.martin.inventory.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.martin.inventory.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    //Item Prepared Data
    String username = "test";
    String password = "pass";

    @Test
    void InitializationTest() {
        //Arrange
        User user = new User(this.username, this.password);

        //Assert
        assertEquals(this.username, user.getUsername());
        assertEquals(this.password, user.getPassword());
    }

    @Test
    void InitializationNullUsernameTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(null, this.password);
        });
    }

    @Test
    void InitializationEmptyUsernameTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("", this.password);
        });
    }

    @Test
    void InitializationNullPasswordTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, null);
        });
    }

    @Test
    void InitializationEmptyPasswordTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = new User(this.username, "");
        });
    }

    @Test
    void UserPasswordChangeTest() {
        //Arrange
        User user = new User(this.username, this.password);
        final String newPassword = "NewPassword";

        //Act
        user.setPassword(newPassword);

        //Assert
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void UserNullPasswordChangeTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, this.password);
            user.setPassword(null);
        });
    }

    @Test
    void UserEmptyPasswordChangeTest() {
        //Arrange
        User user = new User(this.username, this.password);

        //Act
        boolean changedValue = user.setPassword("");

        //Assert
        assertEquals(this.password, user.getPassword());
        assertFalse(changedValue);
    }
}
