package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.mapper.IngredientRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.model.Price;
import com.tojo.examerestaurantspringboot.service.exception.ClientException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {
    private IngredientRestMapper ingredientRestMapper;
    private IngredientCrudOperations ingredientCrudOperation;

    public IngredientService(IngredientRestMapper ingredientRestMapper, IngredientCrudOperations ingredientCrudOperation) {
        this.ingredientRestMapper = ingredientRestMapper;
        this.ingredientCrudOperation = ingredientCrudOperation;
    }

    public List<IngredientRest> getAllIngredient(Integer minPrice, Integer maxPrice) {
        if ((minPrice != null && minPrice < 0) || (maxPrice != null && maxPrice < 0)) {
            throw new ClientException("PriceMinFilter or PriceMaxFilter is negative");
        }

        if ((minPrice != null && maxPrice != null) && minPrice > maxPrice) {
            throw new ClientException("PriceMinFilter " + minPrice + " is greater than PriceMaxFilter " + maxPrice);
        }

        if (minPrice != null && maxPrice != null) {
            List<Ingredient> ingredients = getIngredientBetweenMinPriceAndMaxPrice(minPrice, maxPrice);
            return toIngredientRest(ingredients);
        } else if (minPrice != null) {
            List<Ingredient> ingredients = getIngredientByMinPrice(minPrice);
            return toIngredientRest(ingredients);
        } else if (maxPrice != null) {
            List<Ingredient> ingredients = getIngredientByMaxPrice(maxPrice);
            return toIngredientRest(ingredients);
        }

        return toIngredientRest(ingredientCrudOperation.getAll(1, 500));
    }

    public List<Ingredient> getIngredientByMinPrice(int minPrice) {
        return ingredientCrudOperation.getMinIngredientsFilterByPrice(minPrice);
    }

    public List<Ingredient> getIngredientByMaxPrice(int maxPrice) {
        return ingredientCrudOperation.getMaxIngredientsFilterByPrice(maxPrice);
    }

    public List<Ingredient> getIngredientBetweenMinPriceAndMaxPrice(int minPrice, int maxPrice) {
        return ingredientCrudOperation.getIngredientsFilterByPrice(minPrice, maxPrice);
    }

    public Ingredient getIngredientById(int id) {
        return ingredientCrudOperation.findById(id);
    }

    public Ingredient addPrices(int ingredientId, List<Price> pricesToAdd) throws SQLException {
        Ingredient ingredient = ingredientCrudOperation.findById(ingredientId);
        ingredient.addPrices(pricesToAdd);
        List<Ingredient> ingredientsSaved = ingredientCrudOperation.saveAll(List.of(ingredient));
        return ingredientsSaved.getFirst();
    }

    private List<IngredientRest> toIngredientRest(List<Ingredient> ingredients) {
        List<IngredientRest> ingredientRests = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientRests.add(ingredientRestMapper.toRest(ingredient));
        }
        return ingredientRests;
    }
}
