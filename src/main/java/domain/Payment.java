package domain;

public class Payment extends AbstractEntity {
    long id;
    int amount;
    long transactionId;
    long executedAt;

    public Payment() {
    }

    public Payment(long id) {
        this.id = id;
    }

    /**
     * заявка на оплату публикации, отправляется пользователю
     */
    public void create(int amount) {
        this.amount = amount;
    }

    /**
     * закрыть заявку как оплаченную
     *
     * @param transactionId номер транзакции, нужен для осуществления возврата или проверки платежа
     * @return - true, если платеж выполнен, статус публикации: paid;
     * - false, если платеж отклонен, статус публикации: rejected
     */
    public boolean pay(long transactionId) {
        if (transactionId == 0)
            return false;
        this.transactionId = transactionId;
        executedAt = System.currentTimeMillis();
        return true;
    }
}
