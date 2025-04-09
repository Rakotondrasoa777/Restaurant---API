package com.tojo.examerestaurantspringboot.model;

import java.sql.Date;

public class Price {
    private int id;
    private Ingredient ingredient;
    private int price;
    private Date datePrice;

    public Price() {
    }

    public Price(int price) {
        this.price = price;
        this.datePrice = new Date(System.currentTimeMillis());
    }

    public Price(int id, int price, Date datePrice) {
        this.id = id;
        this.price = price;
        this.datePrice = datePrice;
    }

    public Price(int price, Date datePrice) {
        this.id = id;
        this.price = price;
        this.datePrice = datePrice;
    }

    public Price(int id, Ingredient ingredient, int price, Date datePrice) {
        this.id = id;
        this.ingredient = ingredient;
        this.price = price;
        this.datePrice = datePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDatePrice() {
        return datePrice;
    }

    public void setDatePrice(Date datePrice) {
        this.datePrice = datePrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", price=" + price +
                ", datePrice=" + datePrice +
                '}';
    }
}
