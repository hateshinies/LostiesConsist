import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class ConnectionTest {

    @DisplayName("connectionTest")
    @Test
    public void ConnectionTest() throws SQLException {
        ConnectionPool connectionPool = BasicConnectionPool
                .create(Main.connectString, Main.username, Main.password);
        assertTrue(connectionPool.getConnection().isValid(1));
    }
}