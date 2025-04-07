package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientRestController {
    private final IngredientService serviceIngredient;

    public IngredientRestController(IngredientService serviceIngredient) {
        this.serviceIngredient = serviceIngredient;
    }

    @GetMapping("/ingredients")
    public List<IngredientRest> getIngredients(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        return serviceIngredient.getAllIngredient(minPrice, maxPrice);
    }

    @GetMapping("/ingredients/{id}")
    public Ingredient getIngredientById(@PathVariable int id) {
        return serviceIngredient.getIngredientById(id);
    }
}
