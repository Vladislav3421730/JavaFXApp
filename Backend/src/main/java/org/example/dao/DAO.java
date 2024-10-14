package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    void save(T object);
    void deleteById(int id);
    void update(T object);
    Optional<T> findById(int id);
    List<T> findAll();
}
