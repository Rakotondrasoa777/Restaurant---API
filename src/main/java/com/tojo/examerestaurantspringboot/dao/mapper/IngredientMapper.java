package com.tojo.examerestaurantspringboot.dao.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.PriceCrudOperations;
import com.tojo.examerestaurantspringboot.dao.operations.StockMovementCrudOperations;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.model.Price;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

@Component
public class IngredientMapper implements Function<ResultSet, Ingredient> {
    private final PriceCrudOperations priceCrudOperations;
    private final StockMovementCrudOperations stockMovementCrudOperations;

    public IngredientMapper(PriceCrudOperations priceCrudOperations, StockMovementCrudOperations stockMovementCrudOperations) {
        this.priceCrudOperations = priceCrudOperations;
        this.stockMovementCrudOperations = stockMovementCrudOperations;
    }

    @Override
    public Ingredient apply(ResultSet resultSet) {
        int idIngredient = 0;
        try {
            idIngredient = resultSet.getInt("id_ingredient");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Price> prices = priceCrudOperations.findByIdIngredient(idIngredient);
        List<StockMovement> stockMovements = stockMovementCrudOperations.findByIdIngredient(idIngredient);

        Ingredient ingredient = new Ingredient();
        ingredient.setIdIngredient(idIngredient);
        try {
            ingredient.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ingredient.setPrices(prices);
        ingredient.setStockMovements(stockMovements);
        return ingredient;
    }
}
