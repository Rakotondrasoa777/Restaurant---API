package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.dao.operations.DishOrderCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.model.Order;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrderRestMapper implements Function<Order, OrderRest> {
    private final DishOrderCrudOperations dishOrderCrudOperations;

    public OrderRestMapper(DishOrderCrudOperations dishOrderCrudOperations) {
        this.dishOrderCrudOperations = dishOrderCrudOperations;
    }

    @Override
    public OrderRest apply(Order order) {
        return new OrderRest(
                order.getReference(),
                order.getStatus(),
                dishOrderCrudOperations.getDishOrderRestByReference(order.getReference())
        );
    }
}
