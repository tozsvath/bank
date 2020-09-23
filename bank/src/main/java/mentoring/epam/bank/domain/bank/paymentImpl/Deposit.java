package mentoring.epam.bank.domain.bank.paymentImpl;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Balance;
import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.PaymentMethod;
import mentoring.epam.bank.domain.bank.paymentImpl.constants.PaymentConstants;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Deposit implements PaymentMethod {

    private BalanceRepository balanceRepository;

    public Deposit(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.DEPOSIT;
    }

    public TransactionResponse executeTransactionForPaymentMethod(Transaction transaction) {

        TransactionResponse transactionResponse;

        try {
            Optional<Balance> balance = balanceRepository.findByUser(transaction.getUser());

            if (balance.isPresent()) {
                balance.get().depositCash(transaction.getAmount());
            }

            balanceRepository.save(balance.orElse(new Balance(transaction.getUser(), transaction.getAmount())));
            transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), balance.get().getAmount(),PaymentConstants.OK,PaymentType.DEPOSIT);

        } catch (MongoException mongoException) {
            transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), null, String.format(PaymentConstants.MONGO_ERROR + mongoException.getMessage()),PaymentType.DEPOSIT);
        }

        return transactionResponse;
    }
}
