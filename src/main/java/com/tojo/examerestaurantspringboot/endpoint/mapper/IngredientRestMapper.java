package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.PriceRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.StockMovementRest;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientRestMapper {
    @Autowired private PriceRestMapper priceRestMapper;
    @Autowired private StockMovementRestMapper stockMovementRestMapper;
    @Autowired private IngredientCrudOperations ingredientCrudOperations;

    public IngredientRest toRest(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();
        return new IngredientRest(
                ingredient.getIdIngredient(),
                ingredient.getName(),
                ingredient.getActualPrice(),
                ingredient.getActualStockQuantity(),
                prices,
                stockMovementRests
        );
    }
}
