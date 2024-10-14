package org.example.services;

import java.util.List;

public interface Service<T> {
    void Save(T object);
    void DeleteById(int id);
    void Update(T object);
    T FindById(int id);
    List<T> FindAll();
}
