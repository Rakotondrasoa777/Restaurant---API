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
        return List.of();
    }


    public List<Price> updatePriceIngredient(List<Price> pricesToUpdate, int idIngredient) {
        List<Price> savedPrices = new ArrayList<>();

        String sql = "INSERT INTO history_price (id, ingredient_price, date_price, id_ingredient)"+
        " VALUES (?, ?, ?, ?)"+
        " ON CONFLICT (id) DO UPDATE SET ingredient_price = EXCLUDED.ingredient_price, date_price = EXCLUDED.date_price"+
        " RETURNING id, ingredient_price, date_price";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Price price : pricesToUpdate) {
                statement.setInt(1, price.getId());
                statement.setInt(2, price.getPrice());
                statement.setDate(3, price.getDatePrice());
                statement.setInt(4, idIngredient);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    savedPrices.add(priceMapper.apply(resultSet));
                }
            }

            System.out.println(pricesToUpdate);
            return savedPrices;

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<Price> findByIdIngredient(int idIngredient) {
        List<Price> prices = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select p.id, p.ingredient_price, p.date_price from history_price p"
                     + " join ingredient i on p.id_ingredient = i.id_ingredient"
                     + " where p.id_ingredient = ?")) {
            statement.setInt(1, idIngredient);
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
