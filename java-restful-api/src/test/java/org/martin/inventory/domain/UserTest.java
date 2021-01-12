package org.martin.inventory.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import org.martin.inventory.UserRole;
import org.martin.inventory.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    //Item Prepared Data
    String username = "test";
    String password = "pass";
    UserRole role = UserRole.Admin;
    UUID whId = UUID.randomUUID();

    @Test
    void EmptyConstructorTest() {
        //Arrange
        User user = new User();

        //Assert
        assertNotNull(user);
    }

    @Test
    void InitializationTest() {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);

        //Assert
        assertEquals(this.username, user.getUsername());
        assertEquals(this.password, user.getPassword());
    }

    @Test
    void InitializationNullUsernameTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(null, this.password, this.role, this.whId);
        });
    }

    @Test
    void InitializationEmptyUsernameTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("", this.password, this.role, this.whId);
        });
    }

    @Test
    void InitializationNullPasswordTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, null, this.role, this.whId);
        });
    }

    @Test
    void InitializationEmptyPasswordTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = new User(this.username, "", this.role, this.whId);
        });
    }

    @Test
    void UserPasswordChangeTest() {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);
        final String newPassword = "NewPassword";

        //Act
        user.setPassword(newPassword);

        //Assert
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void UserNullPasswordChangeTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            User user = new User(this.username, this.password, this.role, this.whId);
            user.setPassword(null);
        });
    }

    @Test
    void UserEmptyPasswordChangeTest() {
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
    void UserRoleChangeTest(UserRole newRole) {
        //Arrange
        User user = new User(this.username, this.password, this.role, this.whId);

        //Act
        user.setRole(newRole);

        //Assert
        assertEquals(newRole, user.getRole());
    }
}
