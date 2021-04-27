import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.BasicConnectionPool;
import utils.DbProperties;
import utils.SqlCreator;
import utils.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SqlCreatorTest {
    private static Connection connection;
    private static SqlExecutor executor;
    private static SoftAssertions soft;

    @BeforeAll
    public static void connect() throws Exception {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        connection = BasicConnectionPool.create(url, username, password).getConnection();
        executor = new SqlExecutor(connection);
        soft = new SoftAssertions();
    }

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @AfterAll
    public static void disconnect() throws SQLException {
        connection.close();
    }

    @Test
    public void shouldExecuteInsertQuery() throws Exception {
        String actorEmail = new Random().nextInt(999) + "actor@mail.ru";
        Map<String, String> cargo = new HashMap<>();
        cargo.put("entityName", "Actor");
        cargo.put("email", actorEmail);
        SqlCreator creator = new SqlCreator(cargo);

        String email = executor.simpleStringQuery(creator.createQuery);

        soft.assertThat(email).isEqualTo(actorEmail);
    }

    @Test
    public void shouldExecuteUpdateQuery() throws Exception {
        String email = new Random().nextInt(999) + "actor@mail.ru";
        String insertQuery = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        executor.simpleStringQuery(insertQuery);
        Map<String, String> cargo = new HashMap<>();
        cargo.put("entityName", "Actor");
        cargo.put("email", email);
        cargo.put("token", "123bc3e");
        SqlCreator creator = new SqlCreator(cargo);

        String updatedEmail = executor.simpleStringQuery(creator.updateQuery);

        soft.assertThat(updatedEmail).isEqualTo(email);
    }

    @Test
    public void shouldExecuteDeleteQuery() throws Exception {
        String email = new Random().nextInt(999) + "actor@mail.ru";
        String insertQuery = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        executor.simpleStringQuery(insertQuery);
        Map<String, String> cargo = new HashMap<>();
        cargo.put("entityName", "Actor");
        cargo.put("email", email);
        SqlCreator creator = new SqlCreator(cargo);

        String deletedEmail = executor.simpleStringQuery(creator.deleteQuery);

        soft.assertThat(email).isEqualTo(deletedEmail);
    }

    @Test
    public void shouldExecuteGetOneQuery() throws Exception {
        String email = new Random().nextInt(999) + "actor@mail.ru";
        String insertQuery = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        executor.simpleStringQuery(insertQuery);
        Map<String, String> cargo = new HashMap<>();
        cargo.put("entityName", "Actor");
        cargo.put("email", email);
        SqlCreator creator = new SqlCreator(cargo);

        Map<String, String> object = executor.getValuesQuery(creator.getOneQuery).get(0);

        soft.assertThat(object.get("email")).isEqualTo(email);
    }

    @Test
    public void shouldExecuteGetAllQuery() throws Exception {
        Map<String, String> cargo = new HashMap<>();
        cargo.put("entityName", "Actor");
        SqlCreator creator = new SqlCreator(cargo);

        List<Map<String, String>> objects = executor.getValuesQuery(creator.getAllQuery);

        soft.assertThat(objects).isNotEmpty();
    }
}
