package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mariadb://84.38.180.130:3306/matvey_db",
                "matvey", "matvey"
        );
    }

    public static void createOrder(Order order) throws SQLException {
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("""  
                    insert into orders (position, price) values (?, ?)""");
            statement.setString(1, order.position);
            statement.setInt(2, order.price);

            statement.executeUpdate();
        }
    }

    public static List<Order> getOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("""  
                    select * from orders""");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("position"),
                        resultSet.getInt("price")
                ));
            }
        }
        return orders;
    }

    public static void updateOrder(Order order) throws SQLException {
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("""  
                    update orders set price = ?, position = ? where id = ?""");
            statement.setInt(1, order.price);
            statement.setString(2, order.position);
            statement.setInt(3, order.id);

            statement.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
//        Order order = new Order(-1, "Orange", 15);
//        createOrder(order);
//        updateOrderPrice(1, 15);
        List<Order> orders = getOrders();
        System.out.println(orders);
    }
}
