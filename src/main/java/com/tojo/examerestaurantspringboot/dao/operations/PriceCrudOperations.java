package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.PriceMapper;
import com.tojo.examerestaurantspringboot.model.Price;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PriceCrudOperations implements CrudOperations <Price>{
    private final DataSource dataSource;
    private final PriceMapper priceMapper;

    public PriceCrudOperations(DataSource dataSource, PriceMapper priceMapper) {
        this.dataSource = dataSource;
        this.priceMapper = priceMapper;
    }

    @Override
    public List<Price> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public Price findById(int id) {
        return null;
    }

    @Override
    public List<Price> saveAll(List<Price> entities) {
        List<Price> prices = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("insert into history_price (id, ingredient_price, date_price, id_ingredient) values (?, ?, ?, ?)"
                             + " on conflict (id) do nothing"
                             + " returning id,ingredient_price, date_price, id_ingredient");) {
            entities.forEach(entityToSave -> {
                try {
                    statement.setInt(1, entityToSave.getId());
                    statement.setInt(2, entityToSave.getPrice());
                    statement.setDate(3, entityToSave.getDatePrice());
                    statement.setLong(4, entityToSave.getIngredient().getIdIngredient());
                    statement.addBatch();
                } catch (SQLException e) {
                    throw new ServerException(e);
                }
            });
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    prices.add(priceMapper.apply(resultSet));
                }
            }
            return prices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Price> findByIdIngredient(int idIngredient) {
        List<Price> prices = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select p.id, p.ingredient_price, p.date_price from history_price p"
                     + " join ingredient i on p.id_ingredient = i.id_ingredient"
                     + " where p.id_ingredient = ?")) {
            statement.setLong(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Price price = priceMapper.apply(resultSet);
                    prices.add(price);
                }
                return prices;
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
