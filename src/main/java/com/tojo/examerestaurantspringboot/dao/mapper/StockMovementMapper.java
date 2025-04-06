package com.tojo.examerestaurantspringboot.dao.mapper;

import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.model.StockMovementType;
import com.tojo.examerestaurantspringboot.model.Unit;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class StockMovementMapper implements Function<ResultSet, StockMovement> {
    @Override
    public StockMovement apply(ResultSet resultSet) {
        StockMovement stockMovement = new StockMovement();
        try {
            stockMovement.setId(resultSet.getInt("id_stock"));
            stockMovement.setQuantity(resultSet.getDouble("quantity_ingredient_available"));
            stockMovement.setMovementType(StockMovementType.valueOf(resultSet.getString("move_type")));
            stockMovement.setUnit(Unit.valueOf(resultSet.getString("unit")));
            stockMovement.setDateMove(resultSet.getTimestamp("date_of_move"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockMovement;
    }
}
