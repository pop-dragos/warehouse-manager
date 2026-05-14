package DataAccess;

import Connection.ConnectionFactory;
import Model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object class for handling Bill records.
 */
public class BillDAO {
    protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO Bill (id, clientId, productName, quantity, createdAt) VALUES (?,?,?,?,?)";

    /**
     * Inserts a new bill record into the database.
     * @param bill The Bill object to be logged.
     */
    public void insert(Bill bill) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            statement.setInt(1, bill.id());
            statement.setInt(2, bill.clientId());
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
}