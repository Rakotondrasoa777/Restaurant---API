package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.OrderCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderCrudOperations orderCrudOperations;

    public OrderService(OrderCrudOperations orderCrudOperations) {
        this.orderCrudOperations = orderCrudOperations;
    }

    public OrderRest getAllOrdersByReference(String reference) {
        return orderCrudOperations.getOrderByReference(reference);
    }
}
