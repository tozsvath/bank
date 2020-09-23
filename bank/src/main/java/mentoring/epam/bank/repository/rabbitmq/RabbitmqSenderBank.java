package mentoring.epam.bank.repository.rabbitmq;

import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.repository.rabbitmq.config.BankToAtmChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({BankToAtmChannel.class})
public class RabbitmqSenderBank {

    private final MessageChannel messaging;

    @Autowired
    public RabbitmqSenderBank(BankToAtmChannel bankToAtmChannel) {
        this.messaging = bankToAtmChannel.bankToAtm();
    }

    public void sendWithdraw(TransactionResponse transaction) {

        messaging.send(MessageBuilder.withPayload(transaction).build());
    }

    public void sendDeposit(TransactionResponse transaction) {
        messaging.send(MessageBuilder.withPayload(transaction).build());
    }

    public void sendError(TransactionResponse transaction) {
        messaging.send(MessageBuilder.withPayload(transaction).build());
    }
}
