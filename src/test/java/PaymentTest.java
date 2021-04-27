import domain.Payment;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreatePaymentUsingConstructorWithId() {
        long id = 12L;

        Payment payment = new Payment(id);

        soft.assertThat(payment).hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    public void shouldCreateNewPayment() {
        int amount = 1250;
        Payment payment = new Payment();

        payment.create(amount);

        soft.assertThat(payment).hasFieldOrPropertyWithValue("amount", amount);
    }

    @Test
    public void shouldPay() {
        long transactionId = 456987696441L;
        Payment payment = new Payment();

        payment.pay(transactionId);

        soft.assertThat(payment).hasFieldOrPropertyWithValue("transactionId", transactionId);
    }
}
