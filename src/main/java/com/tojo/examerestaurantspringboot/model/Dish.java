package com.tojo.examerestaurantspringboot.model;

import java.util.List;

public class Dish {
    private int id_dish;
    private String name;
    private int unitPrice;
    private List<Ingredient> ingredientList;

    public Dish(int id_dish, String name, int unitPrice, List<Ingredient> ingredientList) {
        this.id_dish = id_dish;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredientList = ingredientList;
    }

    public int getId_dish() {
        return id_dish;
    }

    public String getName() {
        return name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setId_dish(int id_dish) {
        this.id_dish = id_dish;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
