package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.DishOrderCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.DishOrderRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.UpdateDishOrder;
import com.tojo.examerestaurantspringboot.model.DishOrder;
import org.springframework.beans.factory.annotation.Autowired;
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

    public DishOrder toModel(UpdateDishOrder dishOrder) {
        return new DishOrder(
                dishOrder.getIdDishOrder(),
                null,
                dishOrder.getQuantity(),
                null
        );
    }
}
