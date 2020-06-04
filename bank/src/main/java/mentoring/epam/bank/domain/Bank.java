package mentoring.epam.bank.domain;

import com.mongodb.MongoException;
import mentoring.epam.bank.repository.BalanceRepository;
import mentoring.epam.bank.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class Bank {

    private static final String ERROR = "ERROR";
    private static final String OK = "OK";
    private static final String IS_TRANSACTION_SUCCESSFUL = "IsTransactionSuccessfull";
    private static final String NOT_ENOUGH_FOUNDS = "Not enough founds.";
    private static final String NOT_FOUND = "Not user found with this name";

    @Autowired
    BalanceRepository balanceRepository;

    public ResponseEntity<TransactionResponse> depositCash(Transaction transaction) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        String status;
        TransactionResponse transactionResponse;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (isUserExist(balance)) {
                balance.depositCash(transaction.getAmount());
                balanceRepository.save(balance);

            } else {
                balance = new Balance(transaction.getUser(), transaction.getAmount());
                balanceRepository.save(balance);
            }
            transactionResponse = new TransactionResponse("0", transaction.getUser(), balance.getAmount(), OK);
            httpStatus = HttpStatus.OK;
            status = OK;

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
            transactionResponse = new TransactionResponse("0", transaction.getUser(), null, ERROR);
        }

        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<>(transactionResponse, headers, httpStatus);
    }

    private boolean isUserExist(Balance balance) {
        return !Objects.isNull(balance);
    }


    public ResponseEntity<TransactionResponse> withdrawCash(Transaction transaction) {

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
                    //transaction.getId()
                    transactionResponse = new TransactionResponse("0", balance.getUser(), transaction.getAmount(), OK);
                    httpStatus = HttpStatus.OK;
                    status = OK;

                } else {
                    httpStatus = HttpStatus.OK;
                    status = NOT_ENOUGH_FOUNDS;
                    transactionResponse = new TransactionResponse("0", balance.getUser(), 0.0, status);
                }

            } else {

                httpStatus = HttpStatus.NOT_FOUND;
                status = NOT_FOUND;
                transactionResponse = new TransactionResponse("0", transaction.getUser(), transaction.getAmount(), NOT_FOUND);
            }


        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }
        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<>(transactionResponse, headers, httpStatus);
    }

    public ResponseEntity<Balance> getBalance(String username) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        String status;
        Balance balance = null;

        try {
            balance = balanceRepository.findByUser(username);

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }

        return new ResponseEntity<>(balance, headers, httpStatus);
    }

    public ResponseEntity<List<Balance>> getAllBalance() {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        String status;
        List<Balance> balance = null;

        try {
            balance = balanceRepository.findAll();

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }

        return new ResponseEntity<>(balance, headers, httpStatus);
    }

    private boolean hasEnoughFounds(Transaction transaction, Balance balance) {
        return (balance.getAmount() - transaction.getAmount()) >= 0;
    }
}
