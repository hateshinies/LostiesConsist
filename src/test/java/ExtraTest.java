import domain.Extra;
import domain.ExtraType;
import domain.Type;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ExtraTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreateExtraUsingConstructor() {
        long id = 12L;
        Extra extra = new Extra(id);

        soft.assertThat(extra).hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    public void shouldAddExtra() {
        long pubId = 3;
        LocalDate startDate = LocalDate.of(2021, 4, 21);
        int daysQuantity = 4;
        String coordinates = "41,61825;56,36931";
        Extra extra = new Extra();
        Type type = new Type();
        type.create(ExtraType.LOST, 120, "The owner seeks for pet");

        extra.add(pubId,startDate, daysQuantity, coordinates, type);

        soft.assertThat(extra).hasFieldOrPropertyWithValue("startDate", startDate);
    }

    @Test
    public void shouldCalculateReceipt() {
        long pubId = 3;
        LocalDate startDate = LocalDate.of(2021, 4, 21);
        int daysQuantity = 4;
        String coordinates = "41,61825;56,36931";
        Type type = new Type();
        type.create(ExtraType.FOUND, 240, "The owner seeks for pet");
        Extra extra = new Extra();
        extra.add(pubId,startDate, daysQuantity, coordinates, type);

        Integer amount = extra.calcReceipt();

        soft.assertThat(amount).isInstanceOf(Integer.class);
    }

    @Test
    public void shouldPayReceipt() {
        long pubId = 3;
        LocalDate startDate = LocalDate.of(2021, 4, 21);
        int daysQuantity = 4;
        String coordinates = "41,61825;56,36931";
        Extra extra = new Extra();
        Type type = new Type();
        type.create(ExtraType.LOST, 120, "The owner seeks for pet");
        extra.add(pubId,startDate, daysQuantity, coordinates, type);

        extra.payReceipt(14531567);

        soft.assertThat(extra).hasFieldOrPropertyWithValue("isPublishing", true);
    }
}
