package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.endpoint.rest.AddIngredient;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientAndRequiredQuantity;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.model.Dish;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.service.DishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishRestController {
    private final DishService dishService;

    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public List<Dish> getDishes() {
        return dishService.findAll();
    }

    @PutMapping("/dishes/{idDish}/ingredients")
    public Dish addIngredientInDish(@PathVariable int idDish, @RequestBody List<AddIngredient> ingredient) {
        return dishService.addIngredientInDish(idDish, ingredient);
    }
}
