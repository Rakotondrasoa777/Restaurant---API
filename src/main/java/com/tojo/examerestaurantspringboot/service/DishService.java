package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.DishCrudOperations;
import com.tojo.examerestaurantspringboot.model.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private DishCrudOperations dishCrudOperations;

    public DishService(DishCrudOperations dishCrudOperations) {
        this.dishCrudOperations = dishCrudOperations;
    }

    public List<Dish> findAll() {
        return dishCrudOperations.getAll(1, 10);
    }
}
