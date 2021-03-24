import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import repository.JdbcExecutor;
import repository.RepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    static String connectString = "jdbc:postgresql://localhost:5432/LostiesConsist";
    static String username = "postgres";
    static String password = "postgres";

    public static void main(String[] args) throws SQLException {

        ConnectionPool connectionPool = BasicConnectionPool.create(connectString, username, password);
        Connection connection = connectionPool.getConnection();
        RepositoryImpl ownerRepo = new RepositoryImpl(connection, new JdbcExecutor("owners"));
        ownerRepo.getAll();
        connectionPool.releaseConnection(connection);
    }
}
