package utils;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    String getConnectionUrl();

    String getUsername();

    String getPassword();
}
