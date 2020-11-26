package org.martin.inventory.repository;

import org.martin.inventory.model.Item;
import org.martin.inventory.model.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User, UUID> {
    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    public User getById(UUID id) {
        return entityManager.find(User.class, id);
    }

    public User getByUsername(String username)  {
        return entityManager.createQuery("FROM User WHERE username = :user", User.class)
                .setParameter("user", username)
                .getSingleResult();
    }

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
