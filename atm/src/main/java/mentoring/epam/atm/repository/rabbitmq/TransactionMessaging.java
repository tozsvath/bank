package mentoring.epam.atm.repository.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface TransactionMessaging {

        @Output
        MessageChannel atmToBank();

        @Input
        SubscribableChannel bankToAtm();

        @Input
        SubscribableChannel bankToAtmError();
}
