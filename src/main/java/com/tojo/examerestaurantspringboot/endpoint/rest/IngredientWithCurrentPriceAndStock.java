package com.tojo.examerestaurantspringboot.endpoint.rest;

import java.util.List;

public class IngredientWithCurrentPriceAndStock {
    private int id;
    private String name;
    private double currentPrice;
    private double currentStock;
    private List<PriceRest> priceHistorique;
    private List<StockMovementRest> stockMovementHistorique;

    public IngredientWithCurrentPriceAndStock(int id, String name, double currentPrice, double currentStock, List<PriceRest> priceHistorique, List<StockMovementRest> stockMovementHistorique) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.currentStock = currentStock;
        this.priceHistorique = priceHistorique;
        this.stockMovementHistorique = stockMovementHistorique;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public List<PriceRest> getPriceHistorique() {
        return priceHistorique;
    }

    public List<StockMovementRest> getStockMovementHistorique() {
        return stockMovementHistorique;
    }
}
