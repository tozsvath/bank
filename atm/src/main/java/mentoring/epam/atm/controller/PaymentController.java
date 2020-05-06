package mentoring.epam.atm.controller;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.config.ApiConfig;
import mentoring.epam.atm.domain.Atm;
import mentoring.epam.atm.domain.BaseTransaction;
import mentoring.epam.atm.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import responses.DepositResponse;
import responses.WithdrawResponse;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    Atm atm;

    @PostMapping("/withdraw")
    public WithdrawResponse withdrawCash(@RequestBody BaseTransaction baseTransaction) {

        return atm.withdrawCash(baseTransaction);
    }

    @PostMapping("/deposit")
    public DepositResponse depositCash(@RequestBody BaseTransaction baseTransaction) {

        return atm.depositCash(baseTransaction);
    }

}
