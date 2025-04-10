package com.tojo.examerestaurantspringboot.model;

import java.sql.Timestamp;

public class DishOrderStatus {
    private Status dishOrderStatus;
    private Timestamp dateDishOrderStatus;

    public DishOrderStatus(Status dishOrderStatus, Timestamp dateDishOrderStatus) {
        this.dishOrderStatus = dishOrderStatus;
        this.dateDishOrderStatus = dateDishOrderStatus;
    }

    public Status getDishOrderStatus() {
        return dishOrderStatus;
    }

    public void setDishOrderStatus(Status dishOrderStatus) {
        this.dishOrderStatus = dishOrderStatus;
    }

    public Timestamp getDateDishOrderStatus() {
        return dateDishOrderStatus;
    }

    public void setDateDishOrderStatus(Timestamp dateDishOrderStatus) {
        this.dateDishOrderStatus = dateDishOrderStatus;
    }

    @Override
    public String toString() {
        return "DishOrderStatus{" +
                "dishOrderStatus=" + dishOrderStatus +
                ", dateDishOrderStatus=" + dateDishOrderStatus +
                '}';
    }
}
