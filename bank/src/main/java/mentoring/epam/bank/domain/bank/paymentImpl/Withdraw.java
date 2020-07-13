package mentoring.epam.bank.domain.bank.paymentImpl;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Balance;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.Payment;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class Withdraw implements Payment {

    private static final String ERROR = "ERROR";
    private static final String OK = "OK";
    private static final String IS_TRANSACTION_SUCCESSFUL = "IsTransactionSuccessfull";
    private static final String NOT_ENOUGH_FOUNDS = "Not enough founds.";
    private static final String NOT_FOUND = "Not user found with this name";
    public static final String ID = "0";


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
        HttpStatus httpStatus;
        String status;
        TransactionResponse transactionResponse = null;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (isUserExist(balance)) {
                if (hasEnoughFounds(transaction, balance)) {

                    balance.withdrawCash(transaction.getAmount());
                    balanceRepository.save(balance);

                    transactionResponse = new TransactionResponse(ID, balance.getUser(), transaction.getAmount(), OK);
                    httpStatus = HttpStatus.OK;
                    status = OK;

                } else {
                    httpStatus = HttpStatus.OK;
                    status = NOT_ENOUGH_FOUNDS;
                    transactionResponse = new TransactionResponse(ID, balance.getUser(), 0.0, status);
                }

            } else {

                httpStatus = HttpStatus.NOT_FOUND;
                status = NOT_FOUND;
                transactionResponse = new TransactionResponse(ID, transaction.getUser(), transaction.getAmount(), NOT_FOUND);
            }
        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }
        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return transactionResponse;
    }

}
