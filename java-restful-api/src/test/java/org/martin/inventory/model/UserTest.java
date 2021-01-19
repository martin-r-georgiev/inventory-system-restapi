package org.martin.inventory.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    //User Prepared Data
    private final String username = "test";
    private final String password = "pass";
    private final UserRole role = UserRole.Admin;
    private final UUID whId = UUID.randomUUID();

    @Test
    void emptyConstructorTest() {
        //Arrange
        User user = new User();

        //Assert
        assertNotNull(user);
    }

    @Test
    void initializationTest() {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);

        //Assert
        assertEquals(this.username, user.getUsername());
        assertEquals(this.password, user.getPassword());
    }

    @Test
    void initializationNullUsernameTest() {
        assertThrows(NullPointerException.class, () -> {
            User user = new User(null, this.password, this.role, this.whId);
        });
    }

    @Test
    void initializationEmptyUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("", this.password, this.role, this.whId);
        });
    }

    @Test
    void initializationNullPasswordTest() {
        assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, null, this.role, this.whId);
        });
    }

    @Test
    void initializationEmptyPasswordTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User(this.username, "", this.role, this.whId);
        });
    }

    @Test
    void userPasswordChangeTest() {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);
        final String newPassword = "NewPassword";

        //Act
        user.setPassword(newPassword);

        //Assert
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void userNullPasswordChangeTest() {
        assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, this.password, this.role, this.whId);
            user.setPassword(null);
        });
    }

    @Test
    void userEmptyPasswordChangeTest() {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);

        //Act
        boolean changedValue = user.setPassword("");

        //Assert
        assertEquals(this.password, user.getPassword());
        assertFalse(changedValue);
    }

    @ParameterizedTest
    @EnumSource(UserRole.class)
    void userRoleChangeTest(UserRole newRole) {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);

        //Act
        user.setRole(newRole);

        //Assert
        assertEquals(newRole, user.getRole());
    }
}
