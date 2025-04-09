package com.tojo.examerestaurantspringboot.endpoint.rest;


import java.sql.Date;

public class CreateIngredientPrice {
    private int id;
    private int price;
    private Date datePrice;

    public CreateIngredientPrice(int id, int price, Date datePrice) {
        this.id = id;
        this.price = price;
        this.datePrice = datePrice;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Date getDatePrice() {
        return datePrice;
    }
}
