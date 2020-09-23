package mentoring.epam.atm.repository.rabbitmq;

import mentoring.epam.bank.commons.domain.bank.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(TransactionMessaging.class)
public class RabbitmqSenderAtm {


    private MessageChannel messages;

    @Autowired
    public RabbitmqSenderAtm(TransactionMessaging transactionMessaging) {

        this.messages = transactionMessaging.atmToBank();
    }

    public void send(Transaction transaction) {

        this.messages.send(MessageBuilder.withPayload(transaction).build());
    }

    //TODO - atm hova kudlje?
//    public void sendError(Transaction transaction, String routingKey) {
//        repository.sendMessageAtmToBank(transaction,routingKey);
//    }
}
