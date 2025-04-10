package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.endpoint.rest.UpdateDishOrder;
import com.tojo.examerestaurantspringboot.model.DishOrder;
import com.tojo.examerestaurantspringboot.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {
    private OrderService orderService;
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{reference}")
    public OrderRest getOrder(@PathVariable String reference) {
        return orderService.getAllOrdersByReference(reference);
    }

    @PutMapping("/orders/{reference}/dishes")
    public List<DishOrder> updateDishInOrder(@PathVariable String reference, @RequestBody List<UpdateDishOrder> updateDishOrders) {
        System.out.println(updateDishOrders);
        return orderService.updateDishInOrder(reference, updateDishOrders);
    }
}
