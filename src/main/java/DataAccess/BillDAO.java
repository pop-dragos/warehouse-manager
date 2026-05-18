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

    /**
     * Inserts a new bill record into the database.
     * @param bill The Bill object to be logged.
     */
    public void insert(Bill bill) {
        Connection connection = null;
        PreparedStatement statement = null;

        String INSERT_QUERY = "INSERT INTO Bill (id, clientId, productName, quantity, createdAt) VALUES (?,?,?,?,?)";

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

    /**
     * Retrieves all bill records from the database.
     * @return A list of all Bill objects found.
     */
    public List<Bill> findAll() {
        List<Bill> billList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String SELECT_ALL_QUERY = "SELECT * FROM Bill";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int clientId = resultSet.getInt("clientId");
                String productName = resultSet.getString("productName");
                int quantity = resultSet.getInt("quantity");

                java.time.LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();

                Bill bill = new Bill(id, clientId, productName, quantity, createdAt);
                billList.add(bill);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "BillDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return billList;
    }
}