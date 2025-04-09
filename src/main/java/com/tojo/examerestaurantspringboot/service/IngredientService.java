package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.dao.operations.PriceCrudOperations;
import com.tojo.examerestaurantspringboot.dao.operations.StockMovementCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.mapper.IngredientRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.mapper.PriceRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.CreateIngredientPrice;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientWithCurrentPriceAndStock;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.model.Price;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.service.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private IngredientRestMapper ingredientRestMapper;
    private IngredientCrudOperations ingredientCrudOperation;
    private PriceCrudOperations priceCrudOperations;
    private StockMovementCrudOperations stockMovementCrudOperations;

    public IngredientService(IngredientRestMapper ingredientRestMapper, IngredientCrudOperations ingredientCrudOperation, PriceCrudOperations priceCrudOperations, StockMovementCrudOperations stockMovementCrudOperations) {
        this.ingredientRestMapper = ingredientRestMapper;
        this.ingredientCrudOperation = ingredientCrudOperation;
        this.priceCrudOperations = priceCrudOperations;
        this.stockMovementCrudOperations = stockMovementCrudOperations;
    }

    public List<IngredientWithCurrentPriceAndStock> getAllIngredient(Integer minPrice, Integer maxPrice) {
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

    public IngredientRest getIngredientById(int id) {
        return ingredientRestMapper.toRest(ingredientCrudOperation.findById(id));
    }

    public Ingredient addPrices(int ingredientId, List<Price> pricesToAdd) {
        priceCrudOperations.updatePriceIngredient(pricesToAdd, ingredientId);
        return ingredientCrudOperation.findById(ingredientId);
    }

    public Ingredient addStock(int ingredientId, List<StockMovement> stockMovementsToAdd) {
        stockMovementCrudOperations.updateStock(stockMovementsToAdd, ingredientId);
        return ingredientCrudOperation.findById(ingredientId);
    }

    private List<IngredientWithCurrentPriceAndStock> toIngredientRest(List<Ingredient> ingredients) {
        List<IngredientWithCurrentPriceAndStock> ingredientWithCurrentPriceAndStocks = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientWithCurrentPriceAndStocks.add(ingredientRestMapper.toRestWithCurentPriceAndStock(ingredient));
        }
        return ingredientWithCurrentPriceAndStocks;
    }
}
