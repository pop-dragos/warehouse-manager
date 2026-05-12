package DataAccess;

import Connection.ConnectionFactory;
import Model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDAO {
    protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO log (orderID, clientName, productName, quantity, createdAt) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM log";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM log WHERE orderID = ?";

    public void insert(Bill bill) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            statement.setInt(1, bill.orderID());
            statement.setString(2, bill.clientName());
            statement.setString(3, bill.productName());
            statement.setInt(4, bill.quantity());
            statement.setTimestamp(5, Timestamp.valueOf(bill.createdAt()));

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "BillDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<Bill> findAll() {
        List<Bill> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Cream obiectul Record folosind constructorul sau canonic
                Bill bill = new Bill(
                        resultSet.getInt("orderID"),
                        resultSet.getString("clientName"),
                        resultSet.getString("productName"),
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime()
                );
                list.add(bill);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    public Bill findByOrderId(int orderId) {
        Bill bill = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            statement.setInt(1, orderId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                bill = new Bill(
                        resultSet.getInt("orderID"),
                        resultSet.getString("clientName"),
                        resultSet.getString("productName"),
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findByOrderId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return bill;
    }
}
