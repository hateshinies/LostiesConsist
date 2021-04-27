import domain.Extra;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.BasicConnectionPool;
import utils.DbProperties;
import utils.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class SqlExecutorTest {
    static Connection connection;
    static SqlExecutor executor;
    SoftAssertions soft = new SoftAssertions();

    @BeforeAll
    public static void connect() throws Exception {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        connection = BasicConnectionPool.create(url, username, password).getConnection();
        executor = new SqlExecutor(connection);
    }

    @AfterEach
    public void assertAll() throws SQLException {
        soft.assertAll();
    }

    @AfterAll
    public static void disconnect() throws SQLException {
        connection.close();
    }


    @Test
    public void shouldCreateSqlExecutor() {
        SqlExecutor executor = new SqlExecutor(connection);

        soft.assertThat(executor).hasNoNullFieldsOrProperties();
    }

    @Test
    public void shouldExecuteSimpleLongQuery() throws SQLException {
        int id = new Random().nextInt(999);
        String insertQuery = "insert into extra (\"id\",\"startDate\",\"daysQuantity\",coordinates) values ("+ id + ",'2021-2-4',3,'74,91;87,42')";

        long returnedId = executor.simpleLongQuery(insertQuery);

        soft.assertThat(returnedId).isInstanceOf(Long.class);
    }

    @Test
    public void shouldExecuteSimpleStringQuery() throws SQLException {
        String email = new Random().nextInt(999) + "sm@mail.ru";
        String insertQuery = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";

        String returnedEMail = executor.simpleStringQuery(insertQuery);

        soft.assertThat(returnedEMail).isInstanceOf(String.class);
    }

    @Test
    public void shouldExecuteGetValuesQuery() throws SQLException {
        String insertQuery = "select * from actor";

        Map<String, String> actors = executor.getValuesQuery(insertQuery).get(0);

        soft.assertThat(actors).hasFieldOrProperty("email");
    }
}
