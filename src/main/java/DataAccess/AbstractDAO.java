package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

/**
 * Generic Data Access Object class that provides common database operations (CRUD).
 * It uses Reflection to dynamically generate SQL queries and map database result
 * sets to Java objects.
 * @param <T> The type of the entity this DAO handles.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor that uses reflection to determine the class type of the generic parameter T.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Generates a SELECT query filtered by a specific field.
     * @param field The column name used in the WHERE clause.
     * @return The SQL query string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" =?");
        return sb.toString();
    }

    /**
     * Generates a SELECT * query for the entity table.
     * @return The SQL query string.
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Generates an INSERT query based on the fields of the entity class.
     * @return The SQL query string.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equalsIgnoreCase("id")) {
                sb.append(fields[i].getName());
                if (i < fields.length - 1) sb.append(", ");
            }
        }

        sb.append(") VALUES (");
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equalsIgnoreCase("id")) {
                sb.append("?");
                if (i < fields.length - 1) sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Generates an UPDATE query for the entity.
     * @return The SQL query string.
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equalsIgnoreCase("id")) {
                sb.append(fields[i].getName()).append(" = ?");
                if (i < fields.length - 1) sb.append(", ");
            }
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Generates a DELETE query filtered by ID.
     * @return The SQL query string.
     */
    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Searches for a record by its unique identifier.
     * @param id The ID to search for.
     * @return An instance of T, or null if not found.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Retrieves all records from the corresponding table.
     * @return A list of entity instances.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Maps a ResultSet to a list of objects using reflection.
     * @param resultSet The SQL result set.
     * @return A list of instantiated objects.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating object using Reflexion for " + type.getName(), e);
        }
        return list;
    }

    /**
     * Inserts an object into the database.
     * @param t The object to be inserted.
     * @return The inserted object.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            Field[] fields = type.getDeclaredFields();
            int parameterIndex = 1;
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.equalsIgnoreCase("id")) continue;

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(t);

                statement.setObject(parameterIndex, value);
                parameterIndex++;
            }
            statement.executeUpdate();

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(t, generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(generatedKeys);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Updates an existing record in the database.
     * @param t The object containing the values to be updated.
     * @return The updated object.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field[] fields = type.getDeclaredFields();
            int parameterIndex = 1;
            Object idValue = null;

            for (Field field : fields) {
                String fieldName = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();

                if (fieldName.equalsIgnoreCase("id")) {
                    idValue = method.invoke(t);
                    continue;
                }

                Object value = method.invoke(t);
                statement.setObject(parameterIndex, value);
                parameterIndex++;
            }
            statement.setObject(parameterIndex, idValue);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Deletes a record from the database.
     * @param t The object representing the record to be deleted.
     */
    public void delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field field = type.getDeclaredField("id");
            field.setAccessible(true);
            Object idValue = field.get(t);

            statement.setObject(1, idValue);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
