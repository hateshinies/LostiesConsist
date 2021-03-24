import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import repository.DbExecutor;
import repository.RepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {

    static String connectString = "jdbc:postgresql://localhost:5432/LostiesConsist";
    static String username = "postgres";
    static String password = "postgres";

    public static void main(String[] args) throws SQLException {

        ConnectionPool connectionPool = BasicConnectionPool.create(connectString, username, password);
        Connection connection = connectionPool.getConnection();
        RepositoryImpl ownerRepo = new RepositoryImpl(connection, new DbExecutor("owners"));
        List<Owner> owners = ownerRepo.getAll();
        System.out.println(Arrays.toString(owners.get(27).getFieldsArray()));
        connectionPool.releaseConnection(connection);
    }
}
