package mentoring.epam.atm.controller.rest;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.domain.bank.Atm;
import mentoring.epam.atm.repository.rabbitmq.TransactionMessaging;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@EnableBinding(TransactionMessaging.class)
public class PaymentController {

    public static final String AUTHORIZATION = "Authorization";
    private Atm atm;
    private TransactionMessaging rabbitmqSenderAtm;



    @Autowired
    public PaymentController(Atm atm) {
        this.atm = atm;
    }

    //TODO kell e 3 endpoint
    @PostMapping("/atm/withdraw")
    public HttpStatus withdrawCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) throws IOException, TimeoutException {

        atm.withdrawCash(token, transaction);
        return HttpStatus.OK;
    }

    @PostMapping("/atm/deposit")
    public HttpStatus depositCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {

        atm.depositCash(token, transaction);
        return HttpStatus.OK;
    }

}
