package domain;

import java.time.LocalDate;

public class Extra extends AbstractEntity {
    long id;
    long publicationId;
    LocalDate startDate;
    Integer daysQuantity;
    String coordinates;
    Payment payment;
    boolean isPublishing;

    public Extra() {
    }

    public Extra(long id) {
        this.id = id;
    }

    public void add(long publicationId, LocalDate startDate, int daysQuantity, String coordinates, Type type) {
        this.publicationId = publicationId;
        this.startDate = startDate;
        this.daysQuantity = daysQuantity;
        this.coordinates = coordinates;
        payment = new Payment();
        payment.create(daysQuantity * type.price);
    }

    /**
     * метод для выставления суммы оплаты
     */
    public int calcReceipt() {
        return payment.amount;
    }

    public void payReceipt(long transactionId) {
        if (payment.pay(transactionId))
            publish();
    }

    private void publish() {
        isPublishing = true;
    }

}
