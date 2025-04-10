package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.Status;

import java.util.List;

public class OrderRest {
    private String referenceOrder;
    private Status actualStatus;
    private List<DishOrderRest> dishOrders;

    public OrderRest(String referenceOrder, Status actualStatus, List<DishOrderRest> dishOrders) {
        this.referenceOrder = referenceOrder;
        this.actualStatus = actualStatus;
        this.dishOrders = dishOrders;
    }

    public String getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(String referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public Status getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(Status actualStatus) {
        this.actualStatus = actualStatus;
    }

    public List<DishOrderRest> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrderRest> dishOrders) {
        this.dishOrders = dishOrders;
    }
}
