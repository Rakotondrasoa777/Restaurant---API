package com.tojo.examerestaurantspringboot.endpoint.rest;

import com.tojo.examerestaurantspringboot.model.Status;

public class UpdateDishOrder {
    private int idDishOrder;
    private int quantity;
    private Status status;

    public UpdateDishOrder(int idDishOrder, int quantity, Status status) {
        this.idDishOrder = idDishOrder;
        this.quantity = quantity;
        this.status = status;
    }

    public int getIdDishOrder() {
        return idDishOrder;
    }

    public void setIdDishOrder(int idDishOrder) {
        this.idDishOrder = idDishOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateDishOrder{" +
                "idDishOrder=" + idDishOrder +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
