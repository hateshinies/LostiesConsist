import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import utils.BasicConnectionPool;
import utils.ConnectionPool;
import utils.DbProperties;

import java.sql.SQLException;
import java.util.Properties;

public class BasicConnectionPoolTest {

    @Test
    public void shouldCreateConnectionPool() throws SQLException {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        ConnectionPool connectionPool = BasicConnectionPool.create(url,username,password);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(connectionPool).hasFieldOrPropertyWithValue("connectionUrl",url);
        soft.assertAll();
    }
}
