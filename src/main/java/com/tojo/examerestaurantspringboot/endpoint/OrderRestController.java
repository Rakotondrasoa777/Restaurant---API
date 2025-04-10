package com.tojo.examerestaurantspringboot.endpoint;

import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
