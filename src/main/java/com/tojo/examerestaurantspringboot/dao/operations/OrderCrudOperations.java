package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.endpoint.mapper.OrderRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.model.Order;
import com.tojo.examerestaurantspringboot.model.OrderStatus;
import com.tojo.examerestaurantspringboot.model.Status;
import com.tojo.examerestaurantspringboot.service.exception.ClientException;
import com.tojo.examerestaurantspringboot.service.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class OrderCrudOperations implements CrudOperations <Order>{
    private final DataSource dataSource;
    private final DishOrderCrudOperations dishOrderCrudOperations;
    private final OrderRestMapper orderRestMapper;

    public OrderCrudOperations(DataSource dataSource, DishOrderCrudOperations dishOrderCrudOperations, OrderRestMapper orderRestMapper) {
        this.dataSource = dataSource;
        this.dishOrderCrudOperations = dishOrderCrudOperations;
        this.orderRestMapper = orderRestMapper;
    }

    @Override
    public List<Order> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> saveAll(List<Order> entities) {
        return List.of();
    }

    public List<OrderStatus> getOrderStatusesByReferenceOrder(String reference) {
        String sql = "select status, date_order_status from order_status where reference_order = ?";
        List<OrderStatus> orderStatuses = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, reference);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderStatus orderStatus = new OrderStatus(
                            Status.valueOf(resultSet.getString("status")),
                            resultSet.getTimestamp("date_order_status")
                    );

                    orderStatuses.add(orderStatus);
                }
            }
            return orderStatuses;
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public OrderRest getOrderByReference(String reference) {
        String sql = "select * from \"order\" where reference_order = ?";
        Order order = new Order();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, reference);
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    order.setReference(resultSet.getString("reference_order"));
                    order.setStatusOrder(getOrderStatusesByReferenceOrder(order.getReference()));
                    order.setListDishOrder(dishOrderCrudOperations.getDishOrderByReference(order.getReference()));
                }

            } catch (SQLException e) {
                throw new ServerException(e);
            }

            return orderRestMapper.apply(order);
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    public OrderRest confirmOrder(String reference) {
        Status actualStatus = getOrderStatusesByReferenceOrder(reference).stream()
                .max(Comparator.comparing(OrderStatus::getDateOrderStatus))
                .orElse(null)
                .getOrderStatus();

        if (actualStatus == Status.CONFIRMED) {
            throw new ClientException("Order already confirmed");
        }

        String confirmOrderSql = "update order_status set status = ?, date_order_status = ? where reference_order = ?";
        String confirmDishSql = "update dish_order_status set status = ?, date_dish_order_status = ? where reference_order = ?";

        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement1 = connection.prepareStatement(confirmOrderSql)) {
                preparedStatement1.setObject(1, Status.CONFIRMED, Types.OTHER);
                preparedStatement1.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedStatement1.setString(3, reference);

                preparedStatement1.executeUpdate();
            }

            try(PreparedStatement preparedStatement2 = connection.prepareStatement(confirmDishSql)) {
                preparedStatement2.setObject(1, Status.CONFIRMED, Types.OTHER);
                preparedStatement2.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedStatement2.setString(3, reference);

                preparedStatement2.executeUpdate();
            }
            return getOrderByReference(reference);
        } catch (SQLException e) {
            throw new ServerException(e);
        }

    }
}
