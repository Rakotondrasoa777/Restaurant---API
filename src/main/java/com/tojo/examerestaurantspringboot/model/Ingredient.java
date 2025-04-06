package com.tojo.examerestaurantspringboot.model;

import java.util.List;

public class Ingredient {
    private int idIngredient;
    private String name;
    private Unit unit;
    private List<Price> prices;
    private List<StockMovement> stockMovements;

    public Ingredient(int idIngredient, String name, Unit unit, List<Price> prices, List<StockMovement> stockMovements) {
        this.idIngredient = idIngredient;
        this.name = name;
        this.unit = unit;
        this.prices = prices;
        this.stockMovements = stockMovements;
    }

    public Ingredient() {
    }

    public int getIdIngredient() {return idIngredient;}

    public void setIdIngredient(int idIngredient) {this.idIngredient = idIngredient;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Unit getUnit() {return unit;}

    public void setUnit(Unit unit) {this.unit = unit;}

    public List<Price> getPrices() {return prices;}

    public void setPrices(List<Price> prices) {this.prices = prices;}

    public List<StockMovement> getStockMovements() {return stockMovements;}

    public void setStockMovements(List<StockMovement> stockMovements) {this.stockMovements = stockMovements;}

    public List<Price> addPrices(List<Price> prices) {
        if (getPrices() == null || getPrices().isEmpty()){
            return prices;
        }
        prices.forEach(price -> price.setIngredient(this));
        getPrices().addAll(prices);
        return getPrices();
    }
}
