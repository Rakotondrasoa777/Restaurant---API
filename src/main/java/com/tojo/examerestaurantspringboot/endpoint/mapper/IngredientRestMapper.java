package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.*;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientRestMapper {
    @Autowired private PriceRestMapper priceRestMapper;
    @Autowired private StockMovementRestMapper stockMovementRestMapper;

    public IngredientWithCurrentPriceAndStock toRestWithCurentPriceAndStock(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();
        return new IngredientWithCurrentPriceAndStock(
                ingredient.getIdIngredient(),
                ingredient.getName(),
                ingredient.getActualPrice(),
                ingredient.getActualStockQuantity(),
                prices,
                stockMovementRests
        );
    }

    public IngredientRest toRest(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();
        return new IngredientRest(
                ingredient.getIdIngredient(),
                ingredient.getName(),
                prices,
                stockMovementRests
        );
    }

    public IngredientAndRequiredQuantity toRestWithRequiredQuantity(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();

        return new IngredientAndRequiredQuantity(
                ingredient.getIdIngredient(),
                ingredient.getName(),
                ingredient.getActualPrice(),
                ingredient.getActualStockQuantity()
        );
    }
}
