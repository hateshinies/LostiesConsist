import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import repository.OwnerRepository;

import java.sql.SQLException;

public class Main {

    static String connectString = "jdbc:postgresql://localhost:5432/consist";
    static String username = "postgres";
    static String password = "postgres";

    public static void main(String[] args) throws SQLException {

        ConnectionPool connectionPool = BasicConnectionPool.create(connectString, username, password);
        OwnerRepository repo = new OwnerRepository(connectionPool.getConnection());
        repo.getAll();
    }
}
