package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.StockMovementType;
import com.tojo.examerestaurantspringboot.model.Unit;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

public class CreateIngredientStock {
    private int id;
    private StockMovementType movementType;
    private int quantityIngredientAvailable;
    private Unit unit;
    private Timestamp dateMove;

    public CreateIngredientStock(int id, StockMovementType movementType, int quantityIngredientAvailable, Unit unit) {
        this.id = id;
        this.movementType = movementType;
        this.quantityIngredientAvailable = quantityIngredientAvailable;
        this.unit = unit;
        this.dateMove = Timestamp.from(Instant.now());
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

    @Override
    public String toString() {
        return "CreateIngredientStock{" +
                "id=" + id +
                ", movementType=" + movementType +
                ", quantityIngredientAvailable=" + quantityIngredientAvailable +
                ", unit=" + unit +
                ", dateMove=" + dateMove +
                '}';
    }
}
