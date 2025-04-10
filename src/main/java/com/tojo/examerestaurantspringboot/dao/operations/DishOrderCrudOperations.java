package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.endpoint.mapper.DishOrderRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.DishOrderRest;
import com.tojo.examerestaurantspringboot.model.DishOrder;
import com.tojo.examerestaurantspringboot.model.DishOrderStatus;
import com.tojo.examerestaurantspringboot.model.Status;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishOrderCrudOperations implements CrudOperations <DishOrder>{
    private final DataSource dataSource;
    private final DishCrudOperations dishCrudOperations;
    private final DishOrderRestMapper dishOrderRest;
    private final DishOrderRestMapper dishOrderRestMapper;

    public DishOrderCrudOperations(DataSource dataSource, DishCrudOperations dishCrudOperations, DishOrderRestMapper dishOrderRest, DishOrderRestMapper dishOrderRestMapper) {
        this.dataSource = dataSource;
        this.dishCrudOperations = dishCrudOperations;
        this.dishOrderRest = dishOrderRest;
        this.dishOrderRestMapper = dishOrderRestMapper;
    }

    @Override
    public List<DishOrder> getAll(int page, int size) {
        String sql = "select * from dish_order limit ? offset ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size * (page - 1));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = new DishOrder(
                            resultSet.getInt("id_dish_order"),
                            dishCrudOperations.findById(resultSet.getInt("id_dish")),
                            resultSet.getInt("quantity_of_dish"),
                            resultSet.getString("reference_order")
                    );
                    dishOrders.add(dishOrder);
                }
            }

            return dishOrders;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public DishOrder findById(int id) {
        String sql = "select * from dish_order where id_dish_order = ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = new DishOrder(
                            resultSet.getInt("id_dish_order"),
                            dishCrudOperations.findById(resultSet.getInt("id_dish")),
                            resultSet.getInt("quantity_of_dish"),
                            resultSet.getString("reference_order")
                    );
                    dishOrders.add(dishOrder);
                }
            }

            return dishOrders.getFirst();
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public List<DishOrder> saveAll(List<DishOrder> entities) {
        String saveDishOrderSql = "insert into dish_order (id_dish_order, id_dish, quantity_of_dish, reference_order) values (?,?,?,?)";
        String saveDishOrderStatus = "insert into dish_order_status (status, date_dish_order_status, id_dish_order, reference_order) values (?,?,?,?)";

        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement1 = connection.prepareStatement(saveDishOrderSql)) {
                for (DishOrder entity : entities) {
                    preparedStatement1.setInt(1, entity.getIdDishOrder());
                    preparedStatement1.setInt(2, entity.getDish().getIdDish());
                    preparedStatement1.setInt(3, entity.getQuantityOfDish());
                    preparedStatement1.setString(4,entity.getReferenceOrder());
                    preparedStatement1.addBatch();
                }
                preparedStatement1.executeBatch();
            }

            try(PreparedStatement preparedStatement2 = connection.prepareStatement(saveDishOrderStatus)) {
                for (DishOrder entity : entities) {
                    preparedStatement2.setObject(1, Status.CREATED, Types.OTHER);
                    preparedStatement2.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    preparedStatement2.setInt(3, entity.getIdDishOrder());
                    preparedStatement2.setString(4, entity.getReferenceOrder());
                    preparedStatement2.addBatch();
                }
                preparedStatement2.executeBatch();
            }

            return getAll(1, 500);
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<DishOrderRest> getDishOrderRestByReference(String referenceOrder) {
        String sql = "select * from dish_order where reference_order = ?";
        List<DishOrderRest> dishOrders = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, referenceOrder);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = new DishOrder(
                            resultSet.getInt("id_dish_order"),
                            dishCrudOperations.findById(resultSet.getInt("id_dish")),
                            resultSet.getInt("quantity_of_dish"),
                            resultSet.getString("reference_order")
                    );
                    dishOrder.setStatusDishOrder(getDishOrderStatusByReferenceOrder(dishOrder.getReferenceOrder()));
                    dishOrders.add(dishOrderRestMapper.apply(dishOrder));
                }
            }

            return dishOrders;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }


    public List<DishOrder> getDishOrderByReference(String referenceOrder) {
        String sql = "select * from dish_order where reference_order = ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, referenceOrder);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = new DishOrder(
                            resultSet.getInt("id_dish_order"),
                            dishCrudOperations.findById(resultSet.getInt("id_dish")),
                            resultSet.getInt("quantity_of_dish"),
                            resultSet.getString("reference_order")
                    );
                    dishOrder.setStatusDishOrder(getDishOrderStatusByReferenceOrder(referenceOrder));
                    dishOrders.add(dishOrder);
                }
            }

            return dishOrders;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public List<DishOrderStatus> getDishOrderStatusByReferenceOrder(String referenceOrder) {
        String sql = "select status, date_dish_order_status from dish_order_status where reference_order = ?";
        List<DishOrderStatus> dishOrderStatuses = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, referenceOrder);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrderStatus dishOrderStatus = new DishOrderStatus(
                            Status.valueOf(resultSet.getString("status")),
                            resultSet.getTimestamp("date_dish_order_status")
                    );
                    dishOrderStatuses.add(dishOrderStatus);
                }
            }

            return dishOrderStatuses;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }
}
