package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.endpoint.mapper.IngredientRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.CreateIngredientPrice;
import com.tojo.examerestaurantspringboot.endpoint.rest.CreateIngredientStock;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientWithCurrentPriceAndStock;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.model.Price;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class IngredientRestController {
    private final IngredientService serviceIngredient;
    @Autowired private IngredientRestMapper ingredientRestMapper;

    public IngredientRestController(IngredientService serviceIngredient) {
        this.serviceIngredient = serviceIngredient;
    }

    @GetMapping("/ingredients")
    public List<IngredientWithCurrentPriceAndStock> getIngredients(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        return serviceIngredient.getAllIngredient(minPrice, maxPrice);
    }

    @GetMapping("/ingredients/{id}")
    public IngredientRest getIngredientById(@PathVariable int id) {
        return serviceIngredient.getIngredientById(id);
    }

    @PutMapping("/ingredients/{idIngredient}/prices")
    public IngredientRest savePrice(@RequestBody List<CreateIngredientPrice> prices, @PathVariable int idIngredient) throws SQLException {
        List<Price> pricesList = prices.stream()
                .map(ingredientPrice ->
                        new Price(ingredientPrice.getId(), ingredientPrice.getPrice(), ingredientPrice.getDatePrice()))
                .toList();
        Ingredient ingredient = serviceIngredient.addPrices(idIngredient, pricesList);
        ingredient.setIdIngredient(idIngredient);
        IngredientRest ingredientRest = ingredientRestMapper.toRest(ingredient);
        return ingredientRest;
    }
}
