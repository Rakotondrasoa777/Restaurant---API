package com.tojo.examerestaurantspringboot.endpoint.rest;

import java.util.List;

public class DishIngredient {
    private int idDish;
    private String name;
    private List<IngredientAndRequiredQuantity> ingredientAndRequiredQuantityList;
    private int availableDish;
}
