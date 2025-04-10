package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.Unit;

public class AddIngredient {
    private int id;
    private int idIngredient;
    private Unit unit;
    private double quantity;

    public AddIngredient(int id, int idIngredient, Unit unit, double quantity) {
        this.id = id;
        this.idIngredient = idIngredient;
        this.unit = unit;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddIngredient{" +
                "idIngredient=" + idIngredient +
                ", unit=" + unit +
                ", quantity=" + quantity +
                '}';
    }
}
