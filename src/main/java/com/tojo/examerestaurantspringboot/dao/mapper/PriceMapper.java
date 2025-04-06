package com.tojo.examerestaurantspringboot.dao.mapper;

import com.tojo.examerestaurantspringboot.model.Price;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class PriceMapper implements Function<ResultSet, Price> {
    @Override
    public Price apply(ResultSet resultSet) {
        Price price = new Price();
        try {
            price.setId(resultSet.getInt("id"));
            price.setPrice(resultSet.getInt("ingredient_price"));
            price.setDatePrice(resultSet.getDate("date_price"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return price;
    }
}
