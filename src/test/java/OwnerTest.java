import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.JdbcExecutor;
import repository.RepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OwnerTest {

    private static ConnectionPool connectionPool;
    private static Connection connection;
    private static RepositoryImpl repository;

    @BeforeAll
    public static void connect() throws SQLException {
        connectionPool = BasicConnectionPool
                .create("jdbc:postgresql://localhost:5432/LostiesConsist", "postgres", "postgres");
        connection = connectionPool.getConnection();
        JdbcExecutor executor = new JdbcExecutor("owners");
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
        String[] fieldsArray = new String[]{"Arnold", "8-957-148-1123", "hey@arnold.web"};
        owner.setFieldsArray(fieldsArray);
        repository.create(owner);
        assertEquals(repository.getOne(101L).get().getEmail(), "hey@arnold.web");
    }

    @Test
    @DisplayName("getOneTest")
    public void getOwner() throws SQLException {
        assertTrue(repository.getOne(101L).isPresent());
    }

    @Test
    @DisplayName("updateTest")
    public void update() throws SQLException {
        create();
        Owner owner = new Owner();
        String[] fields = new String[]{"Banksy", "8-959-148-4816", "melike@banksy.web", "101"};
        owner.setFieldsArray(fields);
        repository.update(owner);
        Optional<Owner> updatedOwner = repository.getOne(248L);
        //todo проверить тест
//        assertEquals(owner.getEmail(), updatedOwner.get().getEmail());
    }

    @Test
    @DisplayName("deleteTest")
    public void delete() throws SQLException {
        create();
        Owner owner = new Owner();
        repository.delete(101L);
//        Optional<Owner> deletedOwner = ownerRepository.getById(owner.getOwnerId());
//        assertFalse(deletedOwner.isPresent());
    }

    @DisplayName("getAllTest")
    @Test
    public void getOwners() throws SQLException {
//        assertThat(repository.getAll(),);
    }
}
