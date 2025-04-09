package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.IngredientCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.CreateIngredientPrice;
import com.tojo.examerestaurantspringboot.endpoint.rest.PriceRest;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PriceRestMapper implements Function<Price, PriceRest> {
    @Override
    public PriceRest apply(Price price) {
        return new PriceRest(price.getId(), price.getPrice(), price.getDatePrice());
    }

}
