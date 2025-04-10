package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.dao.mapper.IngredientMapper;
import com.tojo.examerestaurantspringboot.endpoint.mapper.IngredientRestMapper;
import com.tojo.examerestaurantspringboot.model.Dish;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishCrudOperations implements CrudOperations <Dish>{
    private final DataSource dataSource;
    private final IngredientMapper ingredientMapper;
    private final PriceCrudOperations priceCrudOperations;
    private final StockMovementCrudOperations stockMovementCrudOperations;
    private final IngredientCrudOperations ingredientCrudOperations;
    private final IngredientRestMapper ingredientRestMapper;

    public DishCrudOperations(DataSource dataSource, IngredientMapper ingredientMapper, PriceCrudOperations priceCrudOperations, StockMovementCrudOperations stockMovementCrudOperations, IngredientCrudOperations ingredientCrudOperations, IngredientRestMapper ingredientRestMapper) {
        this.dataSource = dataSource;
        this.ingredientMapper = ingredientMapper;
        this.priceCrudOperations = priceCrudOperations;
        this.stockMovementCrudOperations = stockMovementCrudOperations;
        this.ingredientCrudOperations = ingredientCrudOperations;
        this.ingredientRestMapper = ingredientRestMapper;
    }

    @Override
    public List<Dish> getAll(int page, int size) {
        String sql = "SELECT id_dish, name FROM dish LIMIT ? OFFSET ?";
        List<Dish> dishes = new ArrayList<>();

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)){

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size * (page - 1));

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setIdDish(resultSet.getInt("id_dish"));
                    dish.setName(resultSet.getString("name"));
                    dish.setIngredientList(ingredientCrudOperations.findIngredientAndRequiredQuantityByIdDish(dish.getIdDish()));
                    dishes.add(dish);
                }
            }

            return dishes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish findById(int id) {
        String sql = "SELECT id_dish, name FROM dish where id_dish = ?";
        List<Dish> dishes = new ArrayList<>();

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setIdDish(resultSet.getInt("id_dish"));
                    dish.setName(resultSet.getString("name"));
                    dish.setIngredientList(ingredientCrudOperations.findIngredientAndRequiredQuantityByIdDish(dish.getIdDish()));
                    dishes.add(dish);
                }
            }

            return dishes.getFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> saveAll(List<Dish> entities) {
        return List.of();
    }
}
