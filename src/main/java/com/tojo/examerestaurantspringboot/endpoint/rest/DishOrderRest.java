package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.Status;

public class DishOrderRest {
    private String dishName;
    private int priceDish;
    private Status actualStatus;

    public DishOrderRest(String dishName, int priceDish, Status actualStatus) {
        this.dishName = dishName;
        this.priceDish = priceDish;
        this.actualStatus = actualStatus;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPriceDish() {
        return priceDish;
    }

    public void setPriceDish(int priceDish) {
        this.priceDish = priceDish;
    }

    public Status getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(Status actualStatus) {
        this.actualStatus = actualStatus;
    }
}
