package org.martin.inventory.repository;

import java.util.List;

public interface IRepository<T, ID> {

    public List<T> getAll();
    public T getById(ID id);
    public T save(T obj);
    public T update(T obj);
    public void delete(T obj);
}
