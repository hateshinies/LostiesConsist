import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import org.junit.Test;
import repository.OwnerRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class OwnerTest {

    ConnectionPool connectionPool = BasicConnectionPool
            .create("jdbc:postgresql://localhost:5432/consist", "postgres", "postgres");

    public OwnerTest() throws SQLException {
    }


    @Test
    public void getOwners() throws SQLException {
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connection);
        assertFalse(ownerRepository.getAll().isEmpty());
        connectionPool.releaseConnection(connection);
    }

    @Test
    public void create() throws SQLException {
        Connection connection = connectionPool.getConnection();
        OwnerRepository ownerRepository = new OwnerRepository(connectionPool.getConnection());
        Owner owner = new Owner(248,"Arnold","8-957-148-1123","hey@arnold.web");
        ownerRepository.create(owner);
        assertTrue(ownerRepository.getById(owner.getOwnerId()).isPresent());
        connectionPool.releaseConnection(connection);
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
        owner.setOwnerId(248);
        owner.setName("Banksy");
        owner.setPhone("8-959-148-4816");
        owner.setEmail("melike@banksy.web");
        ownerRepository.update(owner);
        Optional<Owner> updatedOwner = ownerRepository.getById(owner.getOwnerId());
        assertEquals(owner.getEmail(),updatedOwner.get().getEmail());
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
        Optional<Owner> deletedOwner = ownerRepository.getById(owner.getOwnerId());
        assertFalse(deletedOwner.isPresent());
        connectionPool.releaseConnection(connection);
    }
}
