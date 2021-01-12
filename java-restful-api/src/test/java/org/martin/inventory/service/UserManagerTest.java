package org.martin.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martin.inventory.UserRole;
import org.martin.inventory.model.Warehouse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.martin.inventory.model.User;
import org.martin.inventory.repository.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserManagerTest {
    UUID whId = UUID.randomUUID();

    @Mock
    private UserRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private UserManager manager;

    @BeforeEach
    private void setup() {
        lenient().when(manager.entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void UserCreationTest() {
        //Assign
        User user = new User("user", "pass", UserRole.User, this.whId);

        //Act
        when(repository.save(user)).thenReturn(user);
        boolean created = manager.add(user);
        verify(repository).save(user);

        //Assert
        assertTrue(created);
    }

    @Test
    void UserUpdateTest() {
        //Assign
        User user = new User("user", "pass", UserRole.User, this.whId);
        final String userUsername = "user";

        //Act
        when(repository.getById(userUsername)).thenReturn(user);
        when(repository.update(user)).thenReturn(user);
        boolean updated = manager.update(userUsername, user);
        verify(repository).update(user);

        //Assert
        assertTrue(updated);
    }

    @Test
    void UserDeleteTest() {
        //Assign
        User user = new User("user", "pass", UserRole.User, this.whId);

        //Act
        manager.delete(user);
        verify(repository).delete(user);
    }
}
