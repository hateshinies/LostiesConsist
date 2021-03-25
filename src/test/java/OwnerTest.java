import ConnectionPool.BasicConnectionPool;
import ConnectionPool.ConnectionPool;
import domain.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.DbExecutor;
import repository.Entity;
import repository.OwnerRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class OwnerTest {

    private static ConnectionPool connectionPool;
    private static Connection connection;
    private static OwnerRepository repository;

    @BeforeAll
    public static void connect() throws SQLException {
        connectionPool = BasicConnectionPool
                .create("jdbc:postgresql://localhost:5432/LostiesConsist", "postgres", "postgres");
        connection = connectionPool.getConnection();
        String[] fields = new String[]{"name", "phone", "email"};
        Entity entity = new Entity();
        entity.prepareQueries("owners", fields);
        DbExecutor executor = new DbExecutor(entity);
        repository = new OwnerRepository(connection, executor);
    }

    @AfterAll
    public static void disconnect() {
        connectionPool.releaseConnection(connection);
    }

    @Test
    @DisplayName("createTest")
    public void shouldCreateNewOwner() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Arnold", "8-957-148-1123", "hey@arnold.web"};
        owner.setFieldsArray(fields);

        long id = repository.create(owner);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getOne(id).get()).hasFieldOrProperty("fieldsArray");
        soft.assertAll();
    }

    @Test
    @DisplayName("getOneTest")
    public void getOwner() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Arnold", "8-957-148-1123", "hey@arnold.web"};
        owner.setFieldsArray(fields);

        long id = repository.create(owner);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getOne(id).get()).hasFieldOrProperty("fieldsArray");
        soft.assertAll();
    }


    //update не имеет смысла, в контексте проверки бизнес логики
    @Test
    @DisplayName("updateTest")
    public void update() throws SQLException {
        Owner owner = new Owner();
                String[] fields = new String[]{"Banksy", "8-959-148-4816", "melike@banksy.web"};
        owner.setFieldsArray(fields);

        Long id = repository.create(owner);
        String email = "not@thebest.practice";
        fields = new String[]{"Jessy", "8-959-148-9370", email, id.toString()};
        owner.setFieldsArray(fields);

        repository.update(owner);

        Optional<Owner> ownerFromDB = repository.getOne(id);
        String fieldFromDB = Arrays.toString(ownerFromDB.get().getFieldsArray());
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fieldFromDB).contains(email);
        soft.assertAll();
    }

    //тоже не имеет смысла
    @Test
    @DisplayName("deleteTest")
    public void delete() throws SQLException {
        Owner owner = new Owner();
        String[] fields = new String[]{"Monika", "8-966-238-4412", "geegun@jetski.com"};
        owner.setFieldsArray(fields);

        long id = repository.create(owner);
        repository.delete(id);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getOne(id)).isNotPresent();
        soft.assertAll();
    }

    @Test
    @DisplayName("getAllTest")
    public void getOwners() throws SQLException {
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(repository.getAll()).hasAtLeastOneElementOfType(Owner.class);
        soft.assertAll();
    }
}
