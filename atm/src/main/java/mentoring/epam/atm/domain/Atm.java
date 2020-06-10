package mentoring.epam.atm.domain;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.config.ApiConfig;
import mentoring.epam.atm.rabbitmq.RabbitmqSenderAtm;
import mentoring.epam.bank.commons.rabbitmq.RabbitmqRouteNames;
import mentoring.epam.bank.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class Atm {

    public static final String AUTHORIZATION = "Authorization";
    private ApiConfig apiConfig;
    private RabbitmqSenderAtm rabbitmqSenderAtm;

    @Autowired
    public Atm(ApiConfig apiConfig, RabbitmqSenderAtm rabbitmqSenderAtm) {
        this.apiConfig = apiConfig;
        this.rabbitmqSenderAtm = rabbitmqSenderAtm;
    }

    private static String PROTOCOL = "http";
    private static String PATH_DEPOSIT = "/deposit";
    private static String PATH_WITHDRAW = "/withdraw";
    private static String AUTHENTICATE = "/auth";
    private static String QUEUE_NAME = "hello";
    private static String AUTH = null;
    private static String FRAGMENT = null;
    private static String QUERY = null;

    public void withdrawCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {

        rabbitmqSenderAtm.send(transaction, RabbitmqRouteNames.WITHDRAW.name(),token);
    }

    public void depositCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {

        rabbitmqSenderAtm.send(transaction,RabbitmqRouteNames.DEPOSIT.name(),token);
    }

}
