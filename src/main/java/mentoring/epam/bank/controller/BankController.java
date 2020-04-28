package mentoring.epam.bank.controller;

import com.mongodb.MongoException;
import mentoring.epam.bank.domain.Balance;
import mentoring.epam.bank.domain.DepositResponse;
import mentoring.epam.bank.domain.WithdrawResponse;
import mentoring.epam.bank.repository.BalanceRepository;
import mentoring.epam.bank.repository.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class BankController {

    private static String ERROR = "ERROR";
    private static String OK = "OK";
    private static String IS_TRANSACTION_SUCCESSFUL = "IsTransactionSuccessfull";
    private static String STATUS_STRING = "Status";
    private static String NOT_ENOUGH_FOUNDS = "Not enough founds.";
    private static String NOT_FOUND = "Not user found with this name";

    @Autowired
    BalanceRepository balanceRepository;

    @PostMapping("/deposit")
    ResponseEntity<DepositResponse> depositCash(@RequestBody Transaction transaction) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        String status;
        DepositResponse depositResponse;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (!Objects.isNull(balance)) {
                balance.depositCash(transaction.getAmount());
                balanceRepository.save(balance);
            } else {
                balanceRepository.save(new Balance(transaction.getUser(), transaction.getAmount()));
            }
            depositResponse = new DepositResponse(transaction.getId(), OK);
            httpStatus = HttpStatus.OK;
            status = OK;

            headers.add("id",transaction.getId());
            headers.add("user",balance.getUser());
            headers.add("balance",String.valueOf(balance.getAmount()));

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
            depositResponse = new DepositResponse(transaction.getId(), ERROR);
        }

        headers.add("status",status);

        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<>(depositResponse, headers, httpStatus);
    }

    @PostMapping("/withdraw")
    ResponseEntity<WithdrawResponse> withdrawCash(@RequestBody Transaction transaction) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        String status;
        WithdrawResponse withdraw = null;

        try {
            Balance balance = balanceRepository.findByUser(transaction.getUser());

            if (!Objects.isNull(balance)) {
                if (balance.getAmount() - transaction.getAmount() < 0) {
                    httpStatus = HttpStatus.OK;
                    status = NOT_ENOUGH_FOUNDS;
                    withdraw = new WithdrawResponse(transaction.getId(), balance.getUser(), transaction.getAmount(), status);

                } else {
                    balance.withdrawCash(transaction.getAmount());
                    withdraw = new WithdrawResponse(transaction.getId(), balance.getUser(), transaction.getAmount(), OK);
                    httpStatus = HttpStatus.OK;
                    status = OK;
                }

            } else {

                httpStatus = HttpStatus.NOT_FOUND;
                status = NOT_FOUND;
                withdraw = new WithdrawResponse(transaction.getId(), balance.getUser(), transaction.getAmount(), NOT_FOUND);
            }

            headers.add("id", transaction.getId());
            headers.add("user", balance.getUser());
            headers.add("amount", String.valueOf(transaction.getAmount()));
            headers.add("message", status);

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = mongoException.getMessage();
        }
        headers.add(IS_TRANSACTION_SUCCESSFUL, status);

        return new ResponseEntity<WithdrawResponse>(withdraw, headers, httpStatus);
    }

}
