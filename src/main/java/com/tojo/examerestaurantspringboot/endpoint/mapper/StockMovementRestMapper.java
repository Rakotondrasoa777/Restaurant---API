package com.tojo.examerestaurantspringboot.endpoint.mapper;

import com.tojo.examerestaurantspringboot.endpoint.rest.StockMovementRest;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StockMovementRestMapper implements Function<StockMovement, StockMovementRest> {
    @Override
    public StockMovementRest apply(StockMovement stockMovement) {
        return new StockMovementRest(
                stockMovement.getId(),
                stockMovement.getQuantity(),
                stockMovement.getUnit(),
                stockMovement.getMovementType(),
                stockMovement.getDateMove()
        );
    }
}
