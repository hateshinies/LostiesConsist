import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.OwnerRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnerTest {

    private static ConnectionPool connectionPool;
    private static Connection connection;

    @BeforeClass
    public static void connect() throws SQLException {
        connectionPool = BasicConnectionPool
                .create("jdbc:postgresql://localhost:5432/consist", "postgres", "postgres");
        connection = connectionPool.getConnection();
    }

    @AfterClass
    public static void disconnect() {
        connectionPool.releaseConnection(connection);
    }

    @Test
    public void create() throws SQLException {
//        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connection);
        Owner owner = new Owner();
//        owner = new Owner(248, "Arnold", "8-957-148-1123", "hey@arnold.web");
        String[] fieldsArray = new String[]{"248", "Arnold", "8-957-148-1123", "hey@arnold.web"};
        owner.setFieldsArray(fieldsArray);
        ownerRepository.create(owner);
//        assertTrue(ownerRepository.getById(owner.getOwnerId()).isPresent());
//        connectionPool.releaseConnection(connection);
    }

    @Test
    public void getOwner() throws SQLException {
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connectionPool.getConnection());
        assertTrue(ownerRepository.getById(248).isPresent());
        connectionPool.releaseConnection(connection);
    }

    @Test
    public void update() throws SQLException {
        create();
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connectionPool.getConnection());
        Owner owner = new Owner();
        String[] fields = new String[]{"248", "Banksy", "8-959-148-4816", "melike@banksy.web"};
        owner.setFieldsArray(fields);
        /*owner.setOwnerId(248);
        owner.setName("Banksy");
        owner.setPhone("8-959-148-4816");
        owner.setEmail("melike@banksy.web");*/
        ownerRepository.update(owner);
//        Optional<Owner> updatedOwner = ownerRepository.getById(owner.getOwnerId());
        //todo проверить тест
//        assertEquals(owner.getEmail(), updatedOwner.get().getEmail());
        connectionPool.releaseConnection(connection);
    }

    @Test
    public void delete() throws SQLException {
        create();
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connectionPool.getConnection());
        Owner owner = new Owner();
        owner.setOwnerId(248);
        ownerRepository.delete(owner);
//        Optional<Owner> deletedOwner = ownerRepository.getById(owner.getOwnerId());
//        assertFalse(deletedOwner.isPresent());
        connectionPool.releaseConnection(connection);
    }

    @Test
    public void getOwners() throws SQLException {
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connection);
        assertFalse(ownerRepository.getAll().isEmpty());
        connectionPool.releaseConnection(connection);
    }
}
