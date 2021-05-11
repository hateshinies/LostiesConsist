package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс осуществляет запросы в базу.
 */
public class SqlExecutor {
    private final Connection connection;

    public SqlExecutor(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    /**
     * Метод выполняет простой sql-запрос, подходит для insert, update, delete с id типа long
     *
     * @return ID измененной записи
     */
    public long simpleLongQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = execTransaction(statement);
        long id = 0;
        while (resultSet.next())
            id = resultSet.getLong(1);
        return id;
    }

    /**
     * Метод выполняет простой sql-запрос, подходит для insert, update, delete с id типа String
     *
     * @return ID измененной записи
     */
    public String simpleStringQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = execTransaction(statement);
        String result = "";
        while (resultSet.next())
            result = resultSet.getString(1);
        return result;
    }

    public List<Map<String, String>> getNonDeleted(String query) throws SQLException {
        return getRows(query, false);
    }

    /**
     * Метод возвращает список значений полей каждой строки. Подходит для всех select'ов.
     */
    public List<Map<String, String>> getRows(String query, boolean getDeleted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int columnsCount = resultSet.getMetaData().getColumnCount();

        List<Map<String, String>> objectsList = new ArrayList<>();
        Map<String, String> object = new HashMap<>();
        while (resultSet.next()) {
            for (int i = 0; i < columnsCount; i++) {
                String field = resultSet.getMetaData().getColumnName(i + 1);
                String value = resultSet.getString(i + 1);
                object.put(field, value);
            }
            if (getDeleted)
                objectsList.add(object);
            else if (object.get("isDeleted").equals("false"))
                objectsList.add(object);
        }
        if (objectsList.isEmpty())
            objectsList.add(null);
        return objectsList;
    }

    public Map<String, String> getSingleRow(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            String field = resultSet.getMetaData().getColumnName(i + 1);
            String value = (resultSet.getString(i + 1));
            result.put(field, value);
        }
        return result;
    }

    private ResultSet execTransaction(PreparedStatement statement) throws SQLException {
        statement.executeQuery();
        ResultSet resultSet = statement.getGeneratedKeys();
        String lock = resultSet.getString("version");
        //если здесь еще нет результата, то вызвать getLock повторно
        if (lock.equals(getLock(buildGetLockQuery(statement)))) {
            connection.commit();
        } else {
            connection.rollback();
            throw new SQLException("transaction was rolled back");
        }
        return resultSet;
    }

    public String getLock(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getString("1");
        else {
            throw new SQLException("field version not found");
        }
    }

    private String buildGetLockQuery(PreparedStatement statement) throws SQLException {
        String start = "select version from " + statement.getMetaData().getTableName(1);
        String end = statement.toString().substring(statement.toString().indexOf("where"));
        return start + end;
    }
}
