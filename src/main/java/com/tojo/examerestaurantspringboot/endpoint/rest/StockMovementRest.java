package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.StockMovementType;
import com.tojo.examerestaurantspringboot.model.Unit;

import java.sql.Timestamp;

public class StockMovementRest {
    private int id;
    private double quantity;
    private Unit unit;
    private StockMovementType stockMovementType;
    private Timestamp dateMove;

    public StockMovementRest(int id, double quantity, Unit unit, StockMovementType stockMovementType, Timestamp dateMove) {
        this.id = id;
        this.quantity = quantity;
        this.unit = unit;
        this.stockMovementType = stockMovementType;
        this.dateMove = dateMove;
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public StockMovementType getStockMovementType() {
        return stockMovementType;
    }

    public Timestamp getDateMove() {
        return dateMove;
    }
}
