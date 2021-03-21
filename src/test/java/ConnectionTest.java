import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {
    @Test
    public void getJdbcConnection() throws SQLException {/*
        try(Connection connection = getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }*/
    }

}
