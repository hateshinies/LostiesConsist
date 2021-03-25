package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbExecutor {

    private static Entity entity;

    public DbExecutor(Entity entity) {
        DbExecutor.entity = entity;
    }

    public long insert(Connection connection, String[] fields) throws SQLException {
        String query = entity.CREATE_QUERY;
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < fields.length; i++)
            statement.setString(i + 1, fields[i]);
        System.out.println(statement);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public long update(Connection connection, String[] fields) throws SQLException {
        String query = entity.UPDATE_QUERY;
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < fields.length; i++) {
            if (i == fields.length - 1)
                statement.setLong(i + 1, Long.parseLong(fields[i]));
            else
                statement.setString(i + 1, fields[i]);
        }
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public long delete(Connection connection, Long id) throws SQLException {
        String query = entity.DELETE_QUERY;
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, id);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getLong(1);
    }

    public String[] getById(Connection connection, long id) throws SQLException {
        String query = entity.GET_ONE_QUERY;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
//        resultSet.next();
        //todo здесь использовать готовые поля из переменной

        int columnsCount = resultSet.getMetaData().getColumnCount();
        String[] fields = new String[columnsCount];
        resultSet.next();
        for (int i = 0; i < columnsCount; i++)
            fields[i] = resultSet.getString(i + 1);
        return fields;
    }

    public List<String[]> getAll(Connection connection) throws SQLException {
        String query = entity.GET_ALL_QUERY;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<String[]> fieldsList = new ArrayList<>();
        while (resultSet.next()) {
            int columnsCount = resultSet.getMetaData().getColumnCount();
            String[] fields = new String[columnsCount];
            for (int i = 0; i < columnsCount; i++) {
                fields[i] = resultSet.getString(i + 1);
            }
            fieldsList.add(fields);
        }
        return fieldsList;
    }
}
