package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.endpoint.rest.DishOrderRest;
import com.tojo.examerestaurantspringboot.model.DishOrder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DishOrderRestMapper implements Function<DishOrder, DishOrderRest> {
    @Override
    public DishOrderRest apply(DishOrder dishOrder) {
        return new DishOrderRest(
                dishOrder.getDish().getName(),
                dishOrder.getDish().getDishPrice(),
                dishOrder.getStatus()
        );
    }

}
