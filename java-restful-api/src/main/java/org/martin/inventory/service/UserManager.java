package org.martin.inventory.service;

import org.martin.inventory.model.Item;
import org.martin.inventory.model.User;
import org.martin.inventory.repository.ItemRepository;
import org.martin.inventory.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.UUID;

public class UserManager {
    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("inventory-persistence");
    EntityManager entityManager = managerFactory.createEntityManager();

    @Inject
    UserRepository repository;

    @PostConstruct
    private void init() {
        repository.setEntityManager(entityManager);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getById(UUID id) {
        return repository.getById(id);
    }

    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

    // CRUD

    public boolean add(User user) {
        entityManager.getTransaction().begin();
        repository.save(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user != null;
    }

    public boolean update(UUID userId, User user) {
        entityManager.getTransaction().begin();
        User found = getById(userId);
        if (found != null) {
            found.setUsername(user.getUsername());
            found.setPassword(user.getPassword());
        }
        User updated = repository.update(found);
        entityManager.getTransaction().commit();
        entityManager.close();
        return updated != null;
    }

    public void delete(User user) {
        entityManager.getTransaction().begin();
        repository.delete(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Authentication

    public void authenticate(String username, String password) {
        Query query = entityManager.createQuery("FROM User WHERE username = :user AND password = :pass", User.class);
        query.setParameter("user", username);
        query.setParameter("pass", password);
        List<User> resultSet = query.getResultList();

        if (resultSet.isEmpty()) {
            throw new NotAuthorizedException("Failed to authenticate user.");
        }
    }
}
