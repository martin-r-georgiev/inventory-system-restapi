package org.martin.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.martin.inventory.model.User;
import org.martin.inventory.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserManagerTest {
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
        User user = new User("user", "pass");

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
        User user = new User("user", "pass");
        final String userUsername = "user";

        //Act
        when(repository.getByUsername(userUsername)).thenReturn(user);
        when(repository.update(user)).thenReturn(user);
        boolean updated = manager.update(userUsername, user);
        verify(repository).update(user);

        //Assert
        assertTrue(updated);
    }
}
