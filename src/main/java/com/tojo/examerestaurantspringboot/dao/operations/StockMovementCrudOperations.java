package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.StockMovementMapper;
import com.tojo.examerestaurantspringboot.model.StockMovement;
import com.tojo.examerestaurantspringboot.model.StockMovementType;
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
        return List.of();
    }


    public List<StockMovement> updateStock(List<StockMovement> entities, int idIngredient) {
        List<StockMovement> stockMovements = new ArrayList<>();
        String sql = """
                INSERT INTO stock (id_stock, quantity_ingredient_available, unit, move_type, date_of_move, id_ingredient)
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT (id_stock) DO UPDATE SET
                    quantity_ingredient_available = EXCLUDED.quantity_ingredient_available,
                    unit = EXCLUDED.unit,
                    move_type = EXCLUDED.move_type,
                    date_of_move = EXCLUDED.date_of_move,
                    id_ingredient = EXCLUDED.id_ingredient
                RETURNING id_stock, quantity_ingredient_available, unit, move_type, date_of_move, id_ingredient""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            for (StockMovement entity : entities) {
                statement.setInt(1, entity.getId());
                statement.setDouble(2, entity.getQuantity());
                statement.setObject(3, entity.getUnit(), Types.OTHER);
                statement.setObject(4, entity.getMovementType(), Types.OTHER);
                statement.setTimestamp(5, Timestamp.from(now()));
                statement.setInt(6, idIngredient);
                ResultSet resultSet = statement.executeQuery();

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

    public double getCurrentStockOfIngredientById(int idIngredient) {
        List<StockMovement> stockMovements = new ArrayList<>();
        double result = 0.0;
        String sql = "select * from stock where id_ingredient = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idIngredient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stockMovements.add(stockMovementMapper.apply(resultSet));
            }

            for (StockMovement stockMovement : stockMovements) {
                if (stockMovement.getMovementType() == StockMovementType.ENTER) {
                    result += stockMovement.getQuantity();
                } else if (stockMovement.getMovementType() == StockMovementType.EXIT) {
                    result -= stockMovement.getQuantity();
                }
            }

            if (result < 0) {
                return result *= -1;
            } else {
                return result;
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
