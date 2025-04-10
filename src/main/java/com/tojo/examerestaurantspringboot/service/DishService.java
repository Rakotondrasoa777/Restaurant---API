package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.DishCrudOperations;
import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.mapper.IngredientRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.AddIngredient;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientAndRequiredQuantity;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.model.Dish;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {
    private DishCrudOperations dishCrudOperations;
    private IngredientCrudOperations ingredientCrudOperations;
    private IngredientRestMapper ingredientRestMapper;

    public DishService(DishCrudOperations dishCrudOperations, IngredientCrudOperations ingredientCrudOperations, IngredientRestMapper ingredientRestMapper) {
        this.dishCrudOperations = dishCrudOperations;
        this.ingredientCrudOperations = ingredientCrudOperations;
        this.ingredientRestMapper = ingredientRestMapper;
    }

    public List<Dish> findAll() {
        return dishCrudOperations.getAll(1, 10);
    }

    public Dish addIngredientInDish(int idDish, List<AddIngredient> ingredientsToAdd) {
        ingredientCrudOperations.addIngredientsToDish(ingredientsToAdd, idDish);
        return dishCrudOperations.findById(idDish);
    }
}
