package com.tojo.examerestaurantspringboot.service;

import com.tojo.examerestaurantspringboot.dao.operations.DishOrderCrudOperations;
import com.tojo.examerestaurantspringboot.dao.operations.OrderCrudOperations;
import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.UpdateDishOrder;
import com.tojo.examerestaurantspringboot.model.DishOrder;
import com.tojo.examerestaurantspringboot.model.Order;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {
    private OrderCrudOperations orderCrudOperations;
    private DishOrderCrudOperations dishOrderCrudOperations;

    public OrderService(OrderCrudOperations orderCrudOperations, DishOrderCrudOperations dishOrderCrudOperations) {
        this.orderCrudOperations = orderCrudOperations;
        this.dishOrderCrudOperations = dishOrderCrudOperations;
    }

    public OrderRest getAllOrdersByReference(String reference) {
        return orderCrudOperations.getOrderByReference(reference);
    }

    public List<DishOrder> updateDishInOrder(String reference, List<UpdateDishOrder> updateDishOrders) {
        return dishOrderCrudOperations.updateDishOrderInOrder(reference, updateDishOrders);
    }

    public OrderRest confirmOrder(String reference) {
        return orderCrudOperations.confirmOrder(reference);
    }

    public OrderRest changeStatus(String reference, int idDish) throws SQLException {
        return orderCrudOperations.changeStatusDish(reference, idDish);
    }
}
