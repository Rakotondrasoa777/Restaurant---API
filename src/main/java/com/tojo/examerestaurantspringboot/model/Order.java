package com.tojo.examerestaurantspringboot.model;

import java.util.List;

public class Order {
    private String reference;
    private List<OrderStatus> statusOrder;
    private List<DishOrder> listDishOrder;

    public Order(String reference, List<OrderStatus> statusOrder, List<DishOrder> listDishOrder) {
        this.reference = reference;
        this.statusOrder = statusOrder;
        this.listDishOrder = listDishOrder;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<OrderStatus> getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(List<OrderStatus> statusOrder) {
        this.statusOrder = statusOrder;
    }

    public List<DishOrder> getListDishOrder() {
        return listDishOrder;
    }

    public void setListDishOrder(List<DishOrder> listDishOrder) {
        this.listDishOrder = listDishOrder;
    }
}
