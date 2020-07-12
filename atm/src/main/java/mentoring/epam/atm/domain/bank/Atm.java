package mentoring.epam.atm.domain.bank;

import lombok.extern.slf4j.Slf4j;
import mentoring.epam.atm.repository.rabbitmq.RabbitmqSenderAtm;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.repository.rabbitmq.RabbitmqRouteNames;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@Slf4j
@Component
public class Atm {

    public static final String AUTHORIZATION = "Authorization";
    private final RabbitmqSenderAtm rabbitmqSenderAtm;

    @Autowired
    public Atm(RabbitmqSenderAtm rabbitmqSenderAtm) {
        this.rabbitmqSenderAtm = rabbitmqSenderAtm;
    }

    public void withdrawCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {

        try {
        rabbitmqSenderAtm.send(transaction, RabbitmqRouteNames.WITHDRAW.name(),token);
        } catch (AmqpException amqpException){
            //TODO ERROR QUEUE
            //TODO REMOTE DEBUG
            rabbitmqSenderAtm.send(transaction, RabbitmqRouteNames.ERROR.name(),token);
            log.info("Error in aqm: "+amqpException.getMessage());
        }
    }

    public void depositCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) {
        try {
        rabbitmqSenderAtm.send(transaction,RabbitmqRouteNames.DEPOSIT.name(),token);
        } catch (AmqpException amqpException){
            //TODO ERROR QUEUE
            //TODO REMOTE DEBUG
            rabbitmqSenderAtm.send(transaction,RabbitmqRouteNames.ERROR.name(),token);
            log.info("Error in aqm: "+amqpException.getMessage());
        }
    }

}
