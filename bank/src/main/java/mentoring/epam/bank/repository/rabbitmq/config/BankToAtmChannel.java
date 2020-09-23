package mentoring.epam.bank.repository.rabbitmq.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface BankToAtmChannel {

    @Output
    MessageChannel bankToAtm();

    @Input
    SubscribableChannel atmToBank();

    @Output
    MessageChannel bankToAtmError();
}
