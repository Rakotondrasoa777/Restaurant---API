package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.IngredientMapper;
import com.tojo.examerestaurantspringboot.model.Ingredient;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientCrudOperations implements CrudOperations<Ingredient> {
    private final DataSource dataSource;
    private final IngredientMapper ingredientMapper;
    private final PriceCrudOperations priceCrudOperations;
    private final StockMovementCrudOperations stockMovementCrudOperations;

    public IngredientCrudOperations(DataSource dataSource, IngredientMapper ingredientMapper, PriceCrudOperations priceCrudOperations, StockMovementCrudOperations stockMovementCrudOperations) {
        this.dataSource = dataSource;
        this.ingredientMapper = ingredientMapper;
        this.priceCrudOperations = priceCrudOperations;
        this.stockMovementCrudOperations = stockMovementCrudOperations;
    }

    @Override
    public List<Ingredient> getAll(int page, int size) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select i.id_ingredient, i.name from ingredient i order by i.id_ingredient asc limit ? offset ?")) {
            statement.setInt(1, size);
            statement.setInt(2, size * (page - 1));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ingredient ingredient = ingredientMapper.apply(resultSet);
                    ingredients.add(ingredient);
                }
                return ingredients;
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public Ingredient findById(int idIngredient) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "select id_ingredient, name from ingredient where id_ingredient = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, idIngredient);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Ingredient ingredient = ingredientMapper.apply(resultSet);
                    ingredients.add(ingredient);
                }

                return ingredients.getFirst();
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public List<Ingredient> saveAll(List<Ingredient> entities) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement("insert into ingredient (id_ingredient, name) values (?, ?)"
                                 + " on conflict (id_ingredient) do update set name=excluded.name"
                                 + " returning id_ingredient, name")) {
                entities.forEach(entityToSave -> {
                    try {
                        statement.setInt(1, entityToSave.getIdIngredient());
                        statement.setString(2, entityToSave.getName());
                        statement.addBatch();
                    } catch (SQLException e) {
                        throw new ServerException(e);
                    }
                    priceCrudOperations.saveAll((entityToSave.getPrices()));
                    stockMovementCrudOperations.saveAll((entityToSave.getStockMovements()));
                });
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        ingredients.add(ingredientMapper.apply(resultSet));
                    }
                }
                return ingredients;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getIngredientsFilterByPrice(int minPrice, int maxPrice) {
        String sql = "select i.id_ingredient, i.name\n" +
                "from ingredient i inner join history_price h on i.id_ingredient = h.id_ingredient\n" +
                "where h.date_price = (\n" +
                "select Max(date_price) from history_price where id_ingredient = i.id_ingredient)\n" +
                "and h.ingredient_price between ? and ? ";
        List<Ingredient> ingredients = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ingredients.add(ingredientMapper.apply(resultSet));
                }
                return ingredients;
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<Ingredient> getMinIngredientsFilterByPrice(int minPrice) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "select i.id_ingredient, i.name\n" +
                "from ingredient i inner join history_price h on i.id_ingredient = h.id_ingredient\n" +
                "where h.date_price = (\n" +
                "select Max(date_price) from history_price where id_ingredient = i.id_ingredient)\n" +
                "and h.ingredient_price >= ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, minPrice);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ingredients.add(ingredientMapper.apply(resultSet));
                }
                return ingredients;
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<Ingredient> getMaxIngredientsFilterByPrice(int maxPrice) {
        String sql = "select i.id_ingredient, i.name\n" +
                "from ingredient i inner join history_price h on i.id_ingredient = h.id_ingredient\n" +
                "where h.date_price = (\n" +
                "select Max(date_price) from history_price where id_ingredient = i.id_ingredient)\n" +
                "and h.ingredient_price <= ?";
        List<Ingredient> ingredients = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, maxPrice);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ingredients.add(ingredientMapper.apply(resultSet));
                }

                return ingredients;
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
