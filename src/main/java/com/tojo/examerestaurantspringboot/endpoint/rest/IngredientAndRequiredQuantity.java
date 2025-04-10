package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.StockMovementMapper;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.model.StockMovementType;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientAndRequiredQuantity {
    private int idIngredient;
    private String name;
    private int currentPrice;
    private double currentStock;
    private double requiredQuantity;
    private final DataSource dataSource = new DataSource();
    private final StockMovementMapper stockMovementMapper = new StockMovementMapper();

    public IngredientAndRequiredQuantity() {
        this.currentStock = getCurrentStock();
    }

    public IngredientAndRequiredQuantity(int idIngredient, String name, int currentPrice, double currentStock) {
        this.idIngredient = idIngredient;
        this.name = name;
        this.currentPrice = currentPrice;
        this.currentStock = currentStock;
    }

    public IngredientAndRequiredQuantity(int idIngredient, String name, int currentPrice, double currentStock, double requiredQuantity) {
        this.idIngredient = idIngredient;
        this.name = name;
        this.currentPrice = currentPrice;
        this.currentStock = currentStock;
        this.requiredQuantity = requiredQuantity;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentStock() {
        List<StockMovement> stockMovements = new ArrayList<>();
        double result = 0.0;
        String sql = "select * from stock where id_ingredient = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idIngredient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stockMovements.add(stockMovementMapper.apply(resultSet));
            }

            for (StockMovement stockMovement : stockMovements) {
                if (stockMovement.getMovementType() == StockMovementType.ENTER) {
                    result += stockMovement.getQuantity();
                } else if (stockMovement.getMovementType() == StockMovementType.EXIT) {
                    result -= stockMovement.getQuantity();
                }
            }

            if (result < 0) {
                return result *= -1;
            } else {
                return result;
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public void setCurrentStock(double currentStock) {
        this.currentStock = currentStock;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
