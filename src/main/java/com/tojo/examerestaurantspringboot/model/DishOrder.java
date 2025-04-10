package com.tojo.examerestaurantspringboot.model;

import com.tojo.examerestaurantspringboot.dao.operations.DishOrderCrudOperations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DishOrder {
    private int idDishOrder;
    private Dish dish;
    private int quantityOfDish;
    private List<DishOrderStatus> statusDishOrder;
    private String referenceOrder;

    public DishOrder(int idDishOrder, Dish dish, int quantityOfDish, String referenceOrder) {
        this.idDishOrder = idDishOrder;
        this.dish = dish;
        this.statusDishOrder = new ArrayList<DishOrderStatus>();
        this.quantityOfDish = quantityOfDish;
        this.referenceOrder = referenceOrder;
    }

    public DishOrder(int idDishOrder, Dish dish, int quantityOfDish, List<DishOrderStatus> statusDishOrder, String referenceOrder) {
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

    public String getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(String referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public DishOrderStatus getActualStatus() {
        return statusDishOrder.stream()
                .max(Comparator.comparing(DishOrderStatus::getDateDishOrderStatus))
                .orElse(null);
    }

    public Status getStatus() {
        return statusDishOrder.stream()
                .max(Comparator.comparing(DishOrderStatus::getDateDishOrderStatus))
                .map(DishOrderStatus::getDishOrderStatus)
                .orElse(null);
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "idDishOrder=" + idDishOrder +
                ", dish=" + dish +
                ", quantityOfDish=" + quantityOfDish +
                ", statusDishOrder=" + statusDishOrder +
                ", referenceOrder='" + referenceOrder + '\'' +
                '}';
    }
}
