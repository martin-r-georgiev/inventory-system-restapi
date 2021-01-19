package org.martin.inventory.service;

import com.google.common.hash.Hashing;
import org.martin.inventory.model.UserRole;
import org.martin.inventory.model.User;
import org.martin.inventory.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.NotAuthorizedException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    public User getByUsername(String username) { return repository.getById(username); }

    // CRUD

    public boolean add(User user) {
        entityManager.getTransaction().begin();
        repository.save(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user != null;
    }

    public boolean update(String username, User user) {
        entityManager.getTransaction().begin();
        User found = getByUsername(username);
        if (found != null) {
            found.setPassword(user.getPassword());
            found.setWarehouseId(user.getWarehouseId());
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

    public UserRole authenticate(String username, String password) {

        // SHA-256 Hashing
        String hashedPassword  = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        Query query = entityManager.createQuery("FROM User WHERE username = :user AND password = :pass", User.class);
        query.setParameter("user", username);
        query.setParameter("pass", hashedPassword);
        query.setFirstResult(0).setMaxResults(1);
        User result = (User) query.getSingleResult();

        if (result == null) {
            throw new NotAuthorizedException("Failed to authenticate user.");
        }
        return result.getRole();
    }
}
