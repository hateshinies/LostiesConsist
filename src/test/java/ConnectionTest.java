import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ConnectionTest {

    @DisplayName("connectionTest")
    @Test
    public void connectionTest() throws SQLException {
        ConnectionPool connectionPool = BasicConnectionPool
                .create(Main.connectString, Main.username, Main.password);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(connectionPool.getConnection().isValid(1)).isTrue();
    }
}