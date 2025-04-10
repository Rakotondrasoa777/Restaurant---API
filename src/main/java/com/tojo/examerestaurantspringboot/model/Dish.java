package com.tojo.examerestaurantspringboot.model;
import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.endpoint.rest.IngredientAndRequiredQuantity;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dish {
    private int idDish;
    private String name;
    private List<IngredientAndRequiredQuantity> ingredientList;
    private int availableDish;

    public Dish() {
    }

    public Dish(int idDish, String name, List<IngredientAndRequiredQuantity> ingredientList, int availableDish) {
        this.idDish = idDish;
        this.name = name;
        this.ingredientList = ingredientList;
        this.availableDish = availableDish;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientAndRequiredQuantity> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientAndRequiredQuantity> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getAvailableDish() {
        List<Integer> result = new ArrayList<>();
        for (IngredientAndRequiredQuantity ingredientAndRequiredQuantity : ingredientList) {
             result.add((int) (ingredientAndRequiredQuantity.getCurrentStock() / ingredientAndRequiredQuantity.getRequiredQuantity()));
        }

        return result.stream().min(Integer::compareTo).get();
    }

    public void setAvailableDish(int availableDish) {
        this.availableDish = availableDish;
    }

    public int getDishPrice() {
        DataSource dataSource = new DataSource();
        String sql = "select unit_price from dish where id_dish = ?";
        int result = 0;
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, this.idDish);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result = resultSet.getInt("unit_price");
                }

                return result;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
