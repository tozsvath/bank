package mentoring.epam.atm.controller;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.config.ApiConfig;
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
    ApiConfig apiConfig;

    @PostMapping("/withdraw")
    public WithdrawResponse withdrawCash(@RequestBody BaseTransaction baseTransaction)  {
        String url = "http://"+apiConfig.getHost().trim() + ":" + apiConfig.getPort() + "/withdraw";
        URI uri = null;
        log.info(url);
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.info(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Transaction> request = new HttpEntity<>(new Transaction(baseTransaction.getUser(), baseTransaction.getAmount()));
        ResponseEntity<WithdrawResponse> result = restTemplate.postForEntity(uri, request, WithdrawResponse.class);

        return result.getBody();
    }

    @PostMapping("/deposit")
    public DepositResponse depositCash(@RequestBody BaseTransaction baseTransaction) {
        String url = "http://"+apiConfig.getHost() + ":" + apiConfig.getPort() + "/deposit";
        log.info(url);
        URI uri = null;

        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        log.info(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<BaseTransaction> request = new HttpEntity<>(baseTransaction);

        ResponseEntity<DepositResponse> result = restTemplate.postForEntity(uri, request, DepositResponse.class);

        return result.getBody();
    }

}
