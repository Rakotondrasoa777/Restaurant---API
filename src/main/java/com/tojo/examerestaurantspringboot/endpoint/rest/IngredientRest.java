package com.tojo.examerestaurantspringboot.endpoint.rest;

import java.util.List;

public class IngredientRest {
    private int id;
    private String name;
    private List<PriceRest> prices;
    private List<StockMovementRest> stockMovements;

    public IngredientRest(int id, String name, List<PriceRest> prices, List<StockMovementRest> stockMovements) {
        this.id = id;
        this.name = name;
        this.prices = prices;
        this.stockMovements = stockMovements;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PriceRest> getPrices() {
        return prices;
    }

    public List<StockMovementRest> getStockMovements() {
        return stockMovements;
    }
}
