import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.DbExecutor;
import repository.RepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {

    private static ConnectionPool connectionPool;
    private static Connection connection;
    private static RepositoryImpl repository;

    @BeforeAll
    public static void connect() throws SQLException {
        connectionPool = BasicConnectionPool
                .create("jdbc:postgresql://localhost:5432/LostiesConsist", "postgres", "postgres");
        connection = connectionPool.getConnection();
        DbExecutor executor = new DbExecutor("owners");
        repository = new RepositoryImpl(connection, executor);
    }

    @AfterAll
    public static void disconnect() {
        connectionPool.releaseConnection(connection);
    }

    @Test
    @DisplayName("createTest")
    public void create() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Arnold", "8-957-148-1123", "hey@arnold.web"};
        owner.setFieldsArray(fields);
        repository.create(owner);
        SoftAssertions soft = new SoftAssertions();
        String fieldsFromDB = Arrays.toString(repository.getOne(101L).get().getFieldsArray());
        soft.assertThat(fieldsFromDB).hasToString(Arrays.toString(fields));
    }

    @Test
    @DisplayName("getOneTest")
    public void getOwner() throws SQLException {
        create();
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getOne(101L).get()).hasFieldOrProperty("fieldsArray");
    }

    @Test
    @DisplayName("updateTest")
    public void update() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Banksy", "8-959-148-4816", "melike@banksy.web"};
        owner.setFieldsArray(fields);
        Long id = repository.create(owner);
        fields = new String[]{"Jessy", "8-959-148-9370", "not@thebest.practice", id.toString()};
        owner.setFieldsArray(fields);
        repository.update(owner);

        Optional<Owner> ownerFromDB = repository.getOne(id);
        String fieldFromDB = Arrays.toString(ownerFromDB.get().getFieldsArray());
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fieldFromDB).hasToString(Arrays.toString(fields));
    }

    @Test
    @DisplayName("deleteTest")
    public void delete() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Monika", "8-966-238-4412", "bellous23@jetski.com"};
        owner.setFieldsArray(fields);
        long id = repository.create(owner);
        repository.delete(id);


        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getOne(id)).isNotPresent();
    }

    @Test
    @DisplayName("getAllTest")
    public void getOwners() throws SQLException {
        assertNotEquals(repository.getAll(), new ArrayList<Owner>());
    }
}
