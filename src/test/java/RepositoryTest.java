import domain.Publication;
import domain.PublicationState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import repository.PublicationRepository;
import repository.Repository;
import specification.FindPublicationByState;
import utils.BasicConnectionPool;
import utils.ConnectionPool;
import utils.DbProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class RepositoryTest {

    @Test
    public void shouldCreateRepository() throws SQLException {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        ConnectionPool connectionPool = BasicConnectionPool.create(url, username, password);
        Connection connection = connectionPool.getConnection();

        PublicationRepository repo = new PublicationRepository(connection);
        List<Publication> confirmedPublications = repo.find(new FindPublicationByState(PublicationState.CONFIRMED));

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(confirmedPublications).isNotEmpty();
        soft.assertThat(confirmedPublications).hasAtLeastOneElementOfType(Publication.class);
        soft.assertAll();
    }
}
