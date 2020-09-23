package mentoring.epam.bank.domain.bank.paymentImpl;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Balance;
import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.PaymentMethod;
import mentoring.epam.bank.domain.bank.paymentImpl.constants.PaymentConstants;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Withdraw implements PaymentMethod {

    private BalanceRepository balanceRepository;

    @Autowired
    public Withdraw(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.DEPOSIT;
    }

    @Override
    public TransactionResponse executeTransactionForPaymentMethod(Transaction transaction) {

        TransactionResponse transactionResponse;

        try {
            Optional<Balance> balance = balanceRepository.findByUser(transaction.getUser());
            transactionResponse = createTransactionResponse(balance.get(), transaction);
        } catch (MongoException mongoException) {
            transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), null, PaymentConstants.MONGO_ERROR + mongoException.getMessage(),getPaymentType());
        }
        return transactionResponse;
    }

    private TransactionResponse createTransactionResponse(Balance balance, Transaction transaction) {
        if (isUserExist(balance)) {
            return paymentValidation(balance, transaction);
        } else {
            return noUserFound(transaction);
        }
    }

    private TransactionResponse noUserFound(Transaction transaction) {
        return new TransactionResponse(PaymentConstants.ID, transaction.getUser(), transaction.getAmount(), PaymentConstants.NOT_FOUND,getPaymentType());
    }

    private TransactionResponse paymentValidation(Balance balance, Transaction transaction) {

        if (hasEnoughFounds(transaction, balance)) {

            balance.withdrawCash(transaction.getAmount());
            balanceRepository.save(balance);
            return new TransactionResponse(PaymentConstants.ID, balance.getUser(), transaction.getAmount(), PaymentConstants.OK,getPaymentType());
        } else {
            return new TransactionResponse(PaymentConstants.ID, balance.getUser(), 0.0, PaymentConstants.NOT_ENOUGH_FOUNDS,getPaymentType());
        }
    }

}
