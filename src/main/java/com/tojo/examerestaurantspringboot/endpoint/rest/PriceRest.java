package com.tojo.examerestaurantspringboot.endpoint.rest;

import java.sql.Date;

public class PriceRest {
    private int id;
    private int priceIngredient;
    private Date datePrice;

    public PriceRest(int id, int priceIngredient, Date datePrice) {
        this.id = id;
        this.priceIngredient = priceIngredient;
        this.datePrice = datePrice;
    }

    public int getId() {
        return id;
    }

    public int getPriceIngredient() {
        return priceIngredient;
    }

    public Date getDatePrice() {
        return datePrice;
    }
}
