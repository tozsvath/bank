package mentoring.epam.atm.controller.messaging;

import mentoring.epam.atm.repository.rabbitmq.TransactionMessaging;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(TransactionMessaging.class)
public class RabbitmqListenerAtm {

    @StreamListener("bankToAtm")
    public void handle(@Payload Transaction tr) {

        System.out.println(".....lofasz....." + tr);
    }

    @StreamListener("bankToAtmError")
    public void handleError(@Payload TransactionResponse tr) {

        System.out.println(".....lofasz....." + tr);
    }

}
