package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.StockMovementType;
import com.tojo.examerestaurantspringboot.model.Unit;

import java.sql.Timestamp;

public class CreateIngredientStock {
    private int id;
    private StockMovementType movementType;
    private int quantityIngredientAvailable;
    private Unit unit;
    private Timestamp dateMove;

    public CreateIngredientStock(int id, StockMovementType movementType, int quantityIngredientAvailable, Unit unit, Timestamp dateMove) {
        this.id = id;
        this.movementType = movementType;
        this.quantityIngredientAvailable = quantityIngredientAvailable;
        this.unit = unit;
        this.dateMove = dateMove;
    }

    public int getId() {
        return id;
    }

    public StockMovementType getMovementType() {
        return movementType;
    }

    public int getQuantityIngredientAvailable() {
        return quantityIngredientAvailable;
    }

    public Unit getUnit() {
        return unit;
    }

    public Timestamp getDateMove() {
        return dateMove;
    }
}
