package mentoring.epam.bank.domain.bank.paymentImpl;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Balance;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.Payment;
import mentoring.epam.bank.domain.bank.paymentImpl.constants.PaymentConstants;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class Deposit implements Payment {

    private BalanceRepository balanceRepository;
    private Transaction transaction;

    @Autowired
    public Deposit(BalanceRepository balanceRepository, Transaction transaction) {
        this.balanceRepository = balanceRepository;
        this.transaction = transaction;
    }

    public TransactionResponse executeTransaction() {

        HttpHeaders headers = new HttpHeaders();
        String status;
        TransactionResponse transactionResponse;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());
            if (isUserExist(balance)) {
                balance.depositCash(transaction.getAmount());
            } else {
                balance = new Balance(transaction.getUser(), transaction.getAmount());
            }
            balanceRepository.save(balance);

            transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), balance.getAmount(), PaymentConstants.OK);

            status = PaymentConstants.OK;

        } catch (MongoException mongoException) {

            status = mongoException.getMessage();
            transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), null, PaymentConstants.ERROR);
        }

        headers.add(PaymentConstants.IS_TRANSACTION_SUCCESSFUL, status);

        return transactionResponse;
    }
}
