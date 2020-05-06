package mentoring.epam.atm.domain;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import responses.DepositResponse;
import responses.WithdrawResponse;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class Atm {

    @Autowired
    ApiConfig apiConfig;

    private static String PROTOCOL = "http";
    private static String PATH_DEPOSIT = "/deposit";
    private static String PATH_WITHDRAW = "/withdraw";
    private static String AUTH = null;
    private static String FRAGMENT = null;
    private static String QUERY = null;

    public WithdrawResponse withdrawCash(@RequestBody BaseTransaction baseTransaction) {

        String host = apiConfig.getHost();
        String port = apiConfig.getPort();

        URI uri = null;

        try {
            uri = new URI(PROTOCOL, AUTH, host, Integer.valueOf(port), PATH_WITHDRAW, QUERY, FRAGMENT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Transaction> request = new HttpEntity<>(new Transaction(baseTransaction.getUser(), baseTransaction.getAmount()));
        ResponseEntity<WithdrawResponse> result = restTemplate.postForEntity(uri, request, WithdrawResponse.class);

        return result.getBody();
    }

    public DepositResponse depositCash(@RequestBody BaseTransaction baseTransaction) {

        String host = apiConfig.getHost();
        String port = apiConfig.getPort();

        URI uri = null;

        try {
            uri = new URI(PROTOCOL, AUTH, host, Integer.valueOf(port), PATH_DEPOSIT, QUERY, FRAGMENT);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<BaseTransaction> request = new HttpEntity<>(baseTransaction);

        ResponseEntity<DepositResponse> result = sendRequest(uri, restTemplate, request);

        return result.getBody();
    }

    private ResponseEntity<DepositResponse> sendRequest(URI uri, RestTemplate restTemplate, HttpEntity<BaseTransaction> request) {
        return restTemplate.postForEntity(uri, request, DepositResponse.class);
    }

}
