package mentoring.epam.atm.controller.rest;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.domain.bank.Atm;
import mentoring.epam.atm.repository.rabbitmq.RabbitmqSenderAtm;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
public class PaymentController {

    public static final String AUTHORIZATION = "Authorization";
    private Atm atm;
    private RabbitmqSenderAtm rabbitmqSenderAtm;

    @Autowired
    public PaymentController(Atm atm, RabbitmqSenderAtm rabbitmqSenderAtm) {
        this.atm = atm;
        this.rabbitmqSenderAtm = rabbitmqSenderAtm;
    }

    @PostMapping("/withdraw")
    public HttpStatus withdrawCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) throws IOException, TimeoutException {

        atm.withdrawCash(token, transaction);
        return HttpStatus.OK;
    }

    @PostMapping("/deposit")
    public HttpStatus depositCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {

        atm.depositCash(token, transaction);
        return HttpStatus.OK;
    }

}
