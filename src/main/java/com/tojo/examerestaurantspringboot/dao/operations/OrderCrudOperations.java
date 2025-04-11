package com.tojo.examerestaurantspringboot.dao.operations;

import com.tojo.examerestaurantspringboot.dao.DataSource;
import com.tojo.examerestaurantspringboot.endpoint.mapper.OrderRestMapper;
import com.tojo.examerestaurantspringboot.endpoint.rest.OrderRest;
import com.tojo.examerestaurantspringboot.model.*;
import com.tojo.examerestaurantspringboot.service.exception.ClientException;
import com.tojo.examerestaurantspringboot.service.exception.NotFoundException;
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

    public OrderRest changeStatusDish(String reference, int idDish) throws SQLException {
        Integer idDishOrder = findDishOrderId(reference, idDish);

        if (idDishOrder == null) {
            throw new NotFoundException("Aucun dish_order trouvé pour reference: " + reference + " et id_dish: " + idDish);
        }

        DishOrderStatus currentStatusOfDish = dishOrderCrudOperations.getCurrentStatusOfDish(reference, idDishOrder);

        String sql = """
        INSERT INTO dish_order_status 
        (status, date_dish_order_status, id_dish_order, reference_order)
        VALUES (?, ?, ?, ?)
        """;

        String updateOrderStatusSql = """
                insert into order_status
                (status, date_order_status, reference_order)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            Status newStatus;
            switch (currentStatusOfDish.getDishOrderStatus()) {
                case CONFIRMED:
                    newStatus = Status.IN_PREPARATION;
                    break;
                case IN_PREPARATION:
                    newStatus = Status.COMPLETED;
                    break;
                case COMPLETED:
                    newStatus = Status.SERVED;
                    break;
                case SERVED:
                    throw new ClientException("Dish already served");
                default:
                    throw new ClientException("To continue, CONFIRMED order!");
            }

            stmt.setObject(1, newStatus, Types.OTHER);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(3, idDishOrder);
            stmt.setString(4, reference);

            stmt.executeUpdate();

            updateGlobalOrderStatusIfNeeded(reference);

            return getOrderByReference(reference);
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    private void updateGlobalOrderStatusIfNeeded(String referenceOrder) {
        String commonStatus = allMatchDishOrderStatus(referenceOrder);
        if (commonStatus != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(
                         "INSERT INTO order_status (status, date_order_status, reference_order) VALUES (?, ?, ?)")) {

                stmt.setObject(1, Status.valueOf(commonStatus), Types.OTHER);
                stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                stmt.setString(3, referenceOrder);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new ServerException("Erreur lors de la mise à jour du statut global");
            }
        }
    }
    private String allMatchDishOrderStatus(String referenceOrder) {
        List<DishOrder> dishOrders = dishOrderCrudOperations.getDishOrderByReference(referenceOrder);
        if (dishOrders.isEmpty()) return null;

        Status firstStatus = dishOrderCrudOperations.getCurrentStatusOfDish(referenceOrder, dishOrders.get(0).getIdDishOrder())
                .getDishOrderStatus();

        boolean allSameStatus = dishOrders.stream()
                .allMatch(dish -> {
                    Status currentStatus = dishOrderCrudOperations.getCurrentStatusOfDish(
                            referenceOrder, dish.getIdDishOrder()).getDishOrderStatus();
                    return currentStatus == firstStatus;
                });

        if (allSameStatus) {
            return switch (firstStatus) {
                case IN_PREPARATION, COMPLETED, SERVED -> firstStatus.name();
                default -> null;
            };
        }
        return null;
    }

    private Integer findDishOrderId(String referenceOrder, int idDish) throws SQLException {
        String sql = """
        SELECT id_dish_order 
        FROM dish_order 
        WHERE reference_order = ? AND id_dish = ?
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, referenceOrder);
            stmt.setInt(2, idDish);

            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id_dish_order") : null;
        }
    }

}
