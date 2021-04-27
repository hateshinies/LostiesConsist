import dao.PublicationDao;
import domain.Actor;
import domain.Publication;
import domain.UserRole;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.BasicConnectionPool;
import utils.DbProperties;
import utils.SqlExecutor;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class PublicationDaoTest {
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
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreatePublication() throws Exception {
        PublicationDao publicationDao = new PublicationDao(connection);

        String photo = "C:/Users/newPhoto.png";
        String description = "new descr";
        String email = new Random().nextInt() + "email@mail.ru";
        Actor actor = new Actor(email, UserRole.RESPONDER, null);
        Publication fromWebPublication = new Publication(actor, photo, description);

        Publication publication = new Publication();
        publication.create(fromWebPublication);

        long pubId = publicationDao.create(publication);

        soft.assertThat(pubId).isInstanceOf(Long.class);
    }

    @Test
    public void shouldUpdatePublication() throws Exception {
        String email = new Random().nextInt(999) + "spb@mail.ru";

        String insertActor = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        String insertPub = "insert into publication (\"createdAt\",\"description\",\"photo\",\"state\",\"email\") values ('1619082294352','short descr','C:/Users/newPhoto.png','CONFIRM_WAIT','" + email + "')";

        executor.simpleStringQuery(insertActor);
        long pubId = executor.simpleLongQuery(insertPub);

        PublicationDao publicationDao = new PublicationDao(connection);

        Actor actor = new Actor(email, UserRole.ADMIN, "abcdabcd");
        Publication publicationForUpdate = new Publication(pubId);
        publicationForUpdate.create(new Publication(actor, "D:/Users/newPhoto.png", "old descr"));

        Long result = publicationDao.update(publicationForUpdate);

        soft.assertThat(result).isInstanceOf(Long.class);
    }

    @Test
    public void shouldDeletePublication() throws Exception {
        String email = new Random().nextInt(999) + "spb@mail.ru";
        String insertActor = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        String insertPub = "insert into publication (\"createdAt\",\"description\",\"photo\",\"state\",\"email\") values ('1619082294352','short descr','C:/Users/newPhoto.png','CONFIRM_WAIT','" + email + "')";
        executor.simpleStringQuery(insertActor);
        long pubId = executor.simpleLongQuery(insertPub);
        PublicationDao publicationDao = new PublicationDao(connection);
        Publication fromWebPublication = new Publication(pubId);

        Long result = publicationDao.delete(fromWebPublication);

        soft.assertThat(result).isEqualTo(pubId);
    }

    @Test
    public void shouldGetOnePublication() throws Exception {
        String email = new Random().nextInt(999) + "spb@mail.ru";
        String insertActor = "insert into actor (\"role\",\"email\") values ('RESPONDER','" + email + "')";
        String insertPub = "insert into publication (\"createdAt\",\"description\",\"photo\",\"state\",\"email\") values ('1619082294352','short descr','C:/Users/newPhoto.png','CONFIRM_WAIT','" + email + "')";
        executor.simpleStringQuery(insertActor);
        long pubId = executor.simpleLongQuery(insertPub);
        PublicationDao publicationDao = new PublicationDao(connection);
        Publication fromWebPublication = new Publication(pubId);

        Publication publication = publicationDao.getOne(fromWebPublication);

        soft.assertThat(publication).hasFieldOrPropertyWithValue("id", pubId);
    }

    @Test
    public void shouldGetAllPublications() throws Exception {
        PublicationDao publicationDao = new PublicationDao(connection);

        List<Publication> publicationList = publicationDao.getAll();

        soft.assertThat(publicationList).hasAtLeastOneElementOfType(Publication.class);
    }
}
