import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import org.junit.Test;
import repository.OwnerRepository;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

public class OwnerTest {
    @Test
    public void getOwners() throws SQLException {
        String SQL_QUERY = "select * from owners";
        ConnectionPool connectionPool = BasicConnectionPool
                .create("jdbc:postgresql://localhost:5432/consist", "postgres", "postgres");
        OwnerRepository ownerRepository = new OwnerRepository(connectionPool.getConnection());
        //как проверить результат???
        assertFalse(ownerRepository.getAll().isEmpty());
    }
}
