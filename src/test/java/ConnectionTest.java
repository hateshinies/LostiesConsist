import org.junit.jupiter.api.Test;
import utils.BasicConnectionPool;
import utils.ConnectionPool;
import org.assertj.core.api.SoftAssertions;
import utils.DbProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void shouldCreateValidConnection() throws SQLException, ClassNotFoundException {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        String driver = props.getProperty("jdbc.driver");
        if (driver != null)
            Class.forName("org.postgresql.Driver");
        ConnectionPool connectionPool = BasicConnectionPool.create(url, username, password);

        Connection connection = connectionPool.getConnection();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(connection.isValid(1)).isTrue();
        soft.assertAll();
    }
}