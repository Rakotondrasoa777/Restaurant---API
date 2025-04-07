package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.endpoint.rest.PriceRest;
import com.tojo.examerestaurantspringboot.model.Price;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PriceRestMapper implements Function<Price, PriceRest> {
    @Override
    public PriceRest apply(Price price) {
        return new PriceRest(price.getId(), price.getPrice(), price.getDatePrice());
    }
}
