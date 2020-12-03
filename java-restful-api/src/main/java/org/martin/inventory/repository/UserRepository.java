package org.martin.inventory.repository;

import org.martin.inventory.model.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User, String> {
    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    public User getById(String username) { return entityManager.find(User.class, username); }

    // CRUD
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public void delete(User user) {
        entityManager.remove(user);
    }
}
