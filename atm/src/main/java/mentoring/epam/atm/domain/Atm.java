package mentoring.epam.atm.domain;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.config.ApiConfig;
import mentoring.epam.atm.config.RabbitmqConfig;
import mentoring.epam.atm.rabbitmq.RabbitmqSender;
import mentoring.epam.auth.client.KeycloakClient;
import mentoring.epam.bank.domain.Transaction;
import mentoring.epam.bank.repository.WithdrawDTO;
import mentoring.epam.rabbitmq.listener.RabbitmqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import responses.DepositResponse;
import responses.WithdrawResponse;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class Atm {

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    RabbitmqConfig rabbitmqConfig;

    @Autowired
    RabbitmqSender rabbitmqSender;

    private static String PROTOCOL = "http";
    private static String PATH_DEPOSIT = "/deposit";
    private static String PATH_WITHDRAW = "/withdraw";
    private static String AUTHENTICATE = "/auth";
    private static String QUEUE_NAME = "hello";
    private static String AUTH = null;
    private static String FRAGMENT = null;
    private static String QUERY = null;

    public WithdrawResponse withdrawCash(@RequestHeader("Authorization") String token, @RequestBody Transaction transaction) throws IOException, TimeoutException {

        URI uri = null;

        try {
            uri = new URI(PROTOCOL, AUTH, apiConfig.getHost(), Integer.valueOf(apiConfig.getPort()), PATH_WITHDRAW, QUERY, FRAGMENT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        rabbitmqSender.send(transaction,"withdraw",token);
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(rabbitmqConfig.getHost());
//        factory.setUsername(rabbitmqConfig.getUser());
//        factory.setPassword(rabbitmqConfig.getPassword());
//        factory.setPort(rabbitmqConfig.getPort());
//
//
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            channel.basicPublish("", QUEUE_NAME, null, transaction.getBytes());
//        }


        RestTemplate restTemplate = new RestTemplate();
        WithdrawDTO withdrawDTO = new WithdrawDTO(transaction, token);

        HttpEntity<WithdrawDTO> request = new HttpEntity<>(withdrawDTO);
        ResponseEntity<WithdrawResponse> result = restTemplate.postForEntity(uri, request, WithdrawResponse.class);



        return result.getBody();
    }

    public DepositResponse depositCash(@RequestHeader("Authorization") String token,@RequestBody Transaction transaction) {

        URI uri = null;
        WithdrawDTO withdrawDTO = new WithdrawDTO(transaction, token);
        HttpEntity<WithdrawDTO> request = new HttpEntity<>(withdrawDTO);

        try {
            uri = new URI(PROTOCOL, AUTH, apiConfig.getHost(), Integer.valueOf(apiConfig.getPort()), PATH_DEPOSIT, QUERY, FRAGMENT);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        rabbitmqSender.send(transaction,"deposit",token);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DepositResponse> result = restTemplate.postForEntity(uri, request, DepositResponse.class);
//                sendRequest(uri, restTemplate, request,DepositResponse.class);

        return result.getBody();
    }

//    private KeycloakClient.TokenCollection authenticate(Transaction transaction,KeycloakClient.TokenCollection token) {
//
//        URI uri = null;
//
//        try {
//            uri = new URI(PROTOCOL, AUTH, apiConfig.getHost(), Integer.valueOf(apiConfig.getPort()), AUTHENTICATE, QUERY, FRAGMENT);
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<Transaction> request = new HttpEntity<>(transaction);
//
//        ResponseEntity<KeycloakClient.TokenCollection> result = sendRequest(uri, restTemplate, request, KeycloakClient.TokenCollection.class);
//
//        return result.getBody();
//    }

    private <T> ResponseEntity<T> sendRequest(URI uri, RestTemplate restTemplate, HttpEntity<Transaction> request, Class<T> clazz) {

        // Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
        //         .getGenericSuperclass()).getActualTypeArguments()[0];

        return restTemplate.postForEntity(uri, request, clazz);
    }

}
