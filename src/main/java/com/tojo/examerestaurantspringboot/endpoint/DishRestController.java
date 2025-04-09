package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.model.Dish;
import com.tojo.examerestaurantspringboot.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
