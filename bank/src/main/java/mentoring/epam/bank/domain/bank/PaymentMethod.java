package mentoring.epam.bank.domain.bank;

import mentoring.epam.bank.commons.domain.bank.Balance;
import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;

import java.util.Objects;

public interface PaymentMethod {

    public PaymentType getPaymentType();

    public TransactionResponse executeTransactionForPaymentMethod(Transaction transaction);

    default boolean hasEnoughFounds(Transaction transaction, Balance balance) {
        return (balance.getAmount() - transaction.getAmount()) >= 0;
    }

    default boolean isUserExist(Balance balance) {
        return !Objects.isNull(balance);
    }
}
