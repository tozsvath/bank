package mentoring.epam.atm.controller;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.domain.Atm;
import mentoring.epam.bank.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import responses.DepositResponse;
import responses.WithdrawResponse;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    Atm atm;

    @PostMapping("/withdraw")
    public WithdrawResponse withdrawCash(@RequestHeader("Authorization") String token, @RequestBody Transaction transaction) throws IOException, TimeoutException {

        return atm.withdrawCash(token,transaction);
    }

    @PostMapping("/deposit")
    public DepositResponse depositCash(@RequestHeader("Authorization") String token, @RequestBody Transaction transaction) {

        return atm.depositCash(token,transaction);
    }

}
