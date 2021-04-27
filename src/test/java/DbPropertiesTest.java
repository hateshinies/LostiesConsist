import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import utils.DbProperties;

import java.util.Properties;

public class DbPropertiesTest {

    @Test
    public void shouldReadPropertiesFromFile() {
        Properties props = DbProperties.getProps();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(props).hasFieldOrProperty("jdbc.password");
        soft.assertAll();
    }
}
