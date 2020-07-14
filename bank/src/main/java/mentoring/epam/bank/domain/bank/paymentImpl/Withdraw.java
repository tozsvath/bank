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

public class Withdraw implements Payment {

    private BalanceRepository balanceRepository;
    private Transaction transaction;

    @Autowired
    public Withdraw(BalanceRepository balanceRepository,Transaction transaction) {
        this.balanceRepository = balanceRepository;
        this.transaction = transaction;
    }

    @Override
    public TransactionResponse executeTransaction() {

        HttpHeaders headers = new HttpHeaders();
        String status;
        TransactionResponse transactionResponse = null;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (isUserExist(balance)) {
                if (hasEnoughFounds(transaction, balance)) {

                    balance.withdrawCash(transaction.getAmount());
                    balanceRepository.save(balance);

                    transactionResponse = new TransactionResponse(PaymentConstants.ID, balance.getUser(), transaction.getAmount(), PaymentConstants.OK);
                    status = PaymentConstants.OK;

                } else {
                    status = PaymentConstants.NOT_ENOUGH_FOUNDS;
                    transactionResponse = new TransactionResponse(PaymentConstants.ID, balance.getUser(), 0.0, status);
                }
            } else {

                status = PaymentConstants.NOT_FOUND;
                transactionResponse = new TransactionResponse(PaymentConstants.ID, transaction.getUser(), transaction.getAmount(), PaymentConstants.NOT_FOUND);
            }
        } catch (MongoException mongoException) {

            status = mongoException.getMessage();
        }
        headers.add(PaymentConstants.IS_TRANSACTION_SUCCESSFUL, status);

        return transactionResponse;
    }

}
