package com.tojo.examerestaurantspringboot.model;

import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientAndRequiredQuantity;

import java.util.List;

public class Dish {
    private int idDish;
    private String name;
    private List<IngredientAndRequiredQuantity> ingredientList;
    private int availableDish;

    public Dish() {
    }

    public Dish(int idDish, String name, List<IngredientAndRequiredQuantity> ingredientList, int availableDish) {
        this.idDish = idDish;
        this.name = name;
        this.ingredientList = ingredientList;
        this.availableDish = availableDish;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientAndRequiredQuantity> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientAndRequiredQuantity> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getAvailableDish() {
        return availableDish;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableDish = availableQuantity;
    }
}
