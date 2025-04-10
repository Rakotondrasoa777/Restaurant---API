package com.tojo.examerestaurantspringboot.model;

import java.sql.Timestamp;

public class OrderStatus {
    private Status orderStatus;
    private Timestamp dateOrderStatus;

    public OrderStatus(Status orderStatus, Timestamp dateOrderStatus) {
        this.orderStatus = orderStatus;
        this.dateOrderStatus = dateOrderStatus;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getDateOrderStatus() {
        return dateOrderStatus;
    }

    public void setDateOrderStatus(Timestamp dateOrderStatus) {
        this.dateOrderStatus = dateOrderStatus;
    }
}
