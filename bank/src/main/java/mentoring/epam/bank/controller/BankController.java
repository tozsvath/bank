package mentoring.epam.bank.controller;

import com.mongodb.MongoException;
import mentoring.epam.bank.domain.Balance;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.bank.response.DepositResponse;
import mentoring.epam.bank.response.WithdrawResponse;
import mentoring.epam.bank.repository.BalanceRepository;
import mentoring.epam.bank.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class BankController {

    @Autowired
    private Bank bank;

    @PostMapping("/deposit")
    ResponseEntity<DepositResponse> depositCash(@RequestBody Transaction transaction) {

        return bank.depositCash(transaction);
    }

    @PostMapping("/withdraw")
    ResponseEntity<WithdrawResponse> withdrawCash(@RequestBody Transaction transaction) {

        return bank.withdrawCash(transaction);
    }

}
