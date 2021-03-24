package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcExecutor {

    private final String entityName;

    private static final String CREATE_QUERY = "insert into %s  values(?,?,?)";
    private static final String UPDATE_QUERY = "update %s set name = ?, phone = ?, email = ? where \"id\" = ?";
    private static final String DELETE_QUERY = "delete from %s where \"id\" = ?";
    private static final String GET_ALL_QUERY = "select * from %s";
    private static final String GET_ONE_QUERY = "select * from %s where \"id\" = ?";

    public JdbcExecutor(String entityName) {
        this.entityName = entityName;
    }

    public long insert(Connection connection, String[] fields) throws SQLException {
        String query = String.format(CREATE_QUERY, entityName);
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < fields.length; i++)
            statement.setString(i + 1, fields[i]);
        statement.executeQuery();
        ResultSet resultSet = statement.getGeneratedKeys();
        return resultSet.getLong(1);
    }

    public long update(Connection connection, String[] fields) throws SQLException {
        String query = String.format(UPDATE_QUERY, entityName);
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < fields.length; i++) {
            if (i == fields.length - 1)
                statement.setLong(i + 1, Long.parseLong(fields[i]));
            else
                statement.setString(i + 1, fields[i]);
        }
        statement.executeQuery();
        ResultSet resultSet = statement.getGeneratedKeys();
        return resultSet.getLong(1);
    }

    public long delete(Connection connection, Long id) throws SQLException {
        String query = String.format(DELETE_QUERY, entityName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeQuery();
        ResultSet resultSet = statement.getGeneratedKeys();
        return resultSet.getLong(1);
    }

    public String[] getById(Connection connection, long id) throws SQLException {
        String query = String.format(GET_ONE_QUERY, entityName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int columnsCount = resultSet.getMetaData().getColumnCount();
        String[] fields = new String[columnsCount];
        for (int i = 0; i < columnsCount; i++) {
            fields[i] = resultSet.getString(i);
        }
        /*        String resultId = resultSet.getString("id");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");*/
        return fields;
    }

    public List<String[]> getAll(Connection connection) throws SQLException {
        String query = String.format(GET_ALL_QUERY, entityName);
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<String[]> fieldsList = new ArrayList<>();
        while (resultSet.next()) {
            int columnsCount = resultSet.getMetaData().getColumnCount();
            String[] fields = new String[columnsCount];
            for (int i = 0; i < columnsCount; i++) {
                fields[i] = resultSet.getString(i);
            }
            fieldsList.add(fields);
        }
        return fieldsList;
    }
}
