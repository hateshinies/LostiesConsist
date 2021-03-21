package ConnectionPool;

import java.sql.Connection;

public interface BasicConnectionPool {
    Connection getConnection();

    boolean releaseConnection();

    String getConnectionUrl();

    String getUsername();

    String getPassword();
}
