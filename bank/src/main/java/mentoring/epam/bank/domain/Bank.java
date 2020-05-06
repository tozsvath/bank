package mentoring.epam.bank.domain;

import com.mongodb.MongoException;
import mentoring.epam.bank.repository.BalanceRepository;
import mentoring.epam.bank.response.DepositResponse;
import mentoring.epam.bank.response.WithdrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Bank {

    private static String ERROR = "ERROR";
    private static String OK = "OK";
    private static String IS_TRANSACTION_SUCCESSFUL = "IsTransactionSuccessfull";
    private static String NOT_ENOUGH_FOUNDS = "Not enough founds.";
    private static String NOT_FOUND = "Not user found with this name";

    @Autowired
    BalanceRepository balanceRepository;

    public ResponseEntity<DepositResponse> depositCash(Transaction transaction) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        String status;
        DepositResponse depositResponse;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (isUserExist(balance)) {
                balance.depositCash(transaction.getAmount());
                balanceRepository.save(balance);

            } else {
                balance = new Balance(transaction.getUser(), transaction.getAmount());
                balanceRepository.save(balance);
            }
            depositResponse = new DepositResponse(transaction.getId(),transaction.getUser(),balance.getAmount(), OK);
            httpStatus = HttpStatus.OK;
            status = OK;

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
            depositResponse = new DepositResponse(transaction.getId(),transaction.getUser(),null, ERROR);
        }

        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<>(depositResponse, headers, httpStatus);
    }

    private boolean isUserExist(Balance balance) {
        return !Objects.isNull(balance);
    }


    public ResponseEntity<WithdrawResponse> withdrawCash(Transaction transaction) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        String status;
        WithdrawResponse withdraw = null;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (isUserExist(balance)) {
                if (hasEnoughFounds(transaction, balance)) {

                    balance.withdrawCash(transaction.getAmount());
                    balanceRepository.save(balance);
                    withdraw = new WithdrawResponse(transaction.getId(), balance.getUser(), transaction.getAmount(), OK);
                    httpStatus = HttpStatus.OK;
                    status = OK;

                } else {
                    httpStatus = HttpStatus.OK;
                    status = NOT_ENOUGH_FOUNDS;
                    withdraw = new WithdrawResponse(transaction.getId(), balance.getUser(), 0.0, status);
                }

            } else {

                httpStatus = HttpStatus.NOT_FOUND;
                status = NOT_FOUND;
                withdraw = new WithdrawResponse(transaction.getId(), transaction.getUser(), transaction.getAmount(), NOT_FOUND);
            }


        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }
        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<WithdrawResponse>(withdraw, headers, httpStatus);
    }

    private boolean hasEnoughFounds(Transaction transaction, Balance balance) {
        return (balance.getAmount() - transaction.getAmount()) >= 0;
    }
}
