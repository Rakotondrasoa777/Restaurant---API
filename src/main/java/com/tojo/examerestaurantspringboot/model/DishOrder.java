package com.tojo.examerestaurantspringboot.model;

import java.util.List;

public class DishOrder {
    private int idDishOrder;
    private Dish dish;
    private int quantityOfDish;
    private List<DishOrderStatus> statusDishOrder;
    private int referenceOrder;

    public DishOrder(int idDishOrder, Dish dish, int quantityOfDish, List<DishOrderStatus> statusDishOrder, int referenceOrder) {
        this.idDishOrder = idDishOrder;
        this.dish = dish;
        this.quantityOfDish = quantityOfDish;
        this.statusDishOrder = statusDishOrder;
        this.referenceOrder = referenceOrder;
    }

    public int getIdDishOrder() {
        return idDishOrder;
    }

    public void setIdDishOrder(int idDishOrder) {
        this.idDishOrder = idDishOrder;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantityOfDish() {
        return quantityOfDish;
    }

    public void setQuantityOfDish(int quantityOfDish) {
        this.quantityOfDish = quantityOfDish;
    }

    public List<DishOrderStatus> getStatusDishOrder() {
        return statusDishOrder;
    }

    public void setStatusDishOrder(List<DishOrderStatus> statusDishOrder) {
        this.statusDishOrder = statusDishOrder;
    }

    public int getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(int referenceOrder) {
        this.referenceOrder = referenceOrder;
    }
}
