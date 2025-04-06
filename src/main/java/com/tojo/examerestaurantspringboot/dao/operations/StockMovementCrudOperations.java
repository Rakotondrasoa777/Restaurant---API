package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.StockMovementMapper;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.now;

@Repository
public class StockMovementCrudOperations implements CrudOperations <StockMovement>{
    private final DataSource dataSource;
    private final StockMovementMapper stockMovementMapper;

    public StockMovementCrudOperations(DataSource dataSource, StockMovementMapper stockMovementMapper) {
        this.dataSource = dataSource;
        this.stockMovementMapper = stockMovementMapper;
    }

    @Override
    public List<StockMovement> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public StockMovement findById(int id) {
        return null;
    }

    @Override
    public List<StockMovement> saveAll(List<StockMovement> entities) {
        List<StockMovement> stockMovements = new ArrayList<>();
        String sql = """
                insert into stock (id_stock, quantity_ingredient_available, unit, move_type, date_of_move, id_ingredient)
                values (?, ?, ?, ?, ?, ?)
                on conflict (id) do nothing returning id_stock, quantity_ingredient_available, unit, move_type, date_of_move, id_ingredient""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            entities.forEach(entityToSave -> {
                try {
                    statement.setInt(1, entityToSave.getId());
                    statement.setDouble(2, entityToSave.getQuantity());
                    statement.setString(3, entityToSave.getUnit().name());
                    statement.setString(4, entityToSave.getMovementType().name());
                    statement.setTimestamp(5, Timestamp.from(now()));
                    statement.setInt(6, entityToSave.getIngredient().getIdIngredient());
                    statement.addBatch();
                } catch (SQLException e) {
                    throw new ServerException(e);
                }
            });
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stockMovements.add(stockMovementMapper.apply(resultSet));
                }
            }
            return stockMovements;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<StockMovement> findByIdIngredient(int idIngredient) {
        List<StockMovement> stockMovements = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select s.id_stock, s.quantity_ingredient_available, s.unit, s.move_type, s.date_of_move from stock s"
                             + " join ingredient i on s.id_ingredient = i.id_ingredient"
                             + " where s.id_ingredient = ?")) {
            statement.setInt(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stockMovements.add(stockMovementMapper.apply(resultSet));
                }
                return stockMovements;
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
