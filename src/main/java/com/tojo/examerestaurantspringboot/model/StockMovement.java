package com.tojo.examerestaurantspringboot.model;

import java.sql.Timestamp;

public class StockMovement {
    private int id;
    private Ingredient ingredient;
    private double quantity;
    private Unit unit;
    private StockMovementType movementType;
    private Timestamp dateMove;

    public StockMovement(int id, StockMovementType movementType, double quantity, Unit unit, Timestamp dateMove) {
        this.id = id;
        this.quantity = quantity;
        this.unit = unit;
        this.movementType = movementType;
        this.dateMove = dateMove;
    }

    public StockMovement(int id, Ingredient ingredient, double quantity, Unit unit, StockMovementType movementType, Timestamp dateMove) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.movementType = movementType;
        this.dateMove = dateMove;
    }

    public StockMovement() {
    }

    public StockMovement(double quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public StockMovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(StockMovementType movementType) {
        this.movementType = movementType;
    }

    public Timestamp getDateMove() {
        return dateMove;
    }

    public void setDateMove(Timestamp dateMove) {
        this.dateMove = dateMove;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", movementType=" + movementType +
                ", dateMove=" + dateMove +
                '}';
    }
}
