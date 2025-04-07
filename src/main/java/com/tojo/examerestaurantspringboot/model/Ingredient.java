package com.tojo.examerestaurantspringboot.model;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public List<StockMovement> addStockMovements(List<StockMovement> stockMovements) {
        stockMovements.forEach(stockMovement -> stockMovement.setIngredient(this));
        if (getStockMovements() == null || getStockMovements().isEmpty()){
            return stockMovements;
        }
        getStockMovements().addAll(stockMovements);
        return getStockMovements();
    }

    public int getActualPrice() {
        return findActualPrice().orElse(new Price(0)).getPrice();
    }

    public double getActualStockQuantity() {
        return findActualStockMovement().orElse(new StockMovement(0.0)).getQuantity();
    }

    private Optional<Price> findActualPrice() {
        return prices.stream().max(Comparator.comparing(Price::getDatePrice));
    }

    private Optional<StockMovement> findActualStockMovement() {
        return stockMovements.stream().max(Comparator.comparing(StockMovement::getDateMove));
    }

    public Double getAvailableQuantityAt(Timestamp datetime) {
        List<StockMovement> stockMovementsBeforeToday = stockMovements.stream()
                .filter(stockMovement ->
                        stockMovement.getDateMove().before(datetime)
                                || stockMovement.getDateMove().equals(datetime))
                .toList();
        double quantity = 0;
        for (StockMovement stockMovement : stockMovementsBeforeToday) {
            if (StockMovementType.ENTER.equals(stockMovement.getMovementType())) {
                quantity += stockMovement.getQuantity();
            } else if (StockMovementType.EXIT.equals(stockMovement.getMovementType())) {
                quantity -= stockMovement.getQuantity();
            }
        }
        return quantity;
    }
}
