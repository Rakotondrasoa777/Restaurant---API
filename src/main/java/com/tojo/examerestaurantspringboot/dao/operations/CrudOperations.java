package com.tojo.examerestaurantspringboot.dao.operations;

import java.util.List;

public interface CrudOperations <E>{
    List<E> getAll(int page, int size);

    E findById(int id);

    List<E> saveAll(List<E> entities);
}
