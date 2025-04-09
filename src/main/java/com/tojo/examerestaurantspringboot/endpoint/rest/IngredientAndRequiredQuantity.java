package com.tojo.examerestaurantspringboot.endpoint.rest;

public class IngredientAndRequiredQuantity {
    private int idIngredient;
    private String name;
    private int currentPrice;
    private double currentStock;
    private double requiredQuantity;

    public IngredientAndRequiredQuantity() {
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
        return currentStock;
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
