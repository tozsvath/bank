package mentoring.epam.atm.rabbitmq;

import mentoring.epam.bank.domain.Transaction;
import mentoring.epam.bank.commons.response.TransactionResponse;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqSenderAtm {

    public static final String TOKEN = "token";
    private RabbitTemplate template;

    private DirectExchange direct;

    @Autowired
    public RabbitmqSenderAtm(RabbitTemplate template, DirectExchange direct) {
        this.template = template;
        this.direct = direct;
    }

    public void send(Transaction transaction, String routeKey, String token) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(TOKEN, token);
        Message message = new Message(transaction.getBytes(), messageProperties);

        template.convertAndSend(direct.getName(), routeKey, message);
    }

    public void send(ResponseEntity<TransactionResponse> transaction, String routeKey, String token) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(TOKEN, token);
        Message message = new Message(getBytes(transaction), messageProperties);

        template.convertAndSend(direct.getName(), routeKey, message);
    }

    private byte[] getBytes(ResponseEntity<TransactionResponse> transactionResponseResponseEntity) {

        byte[] data = SerializationUtils.serialize(transactionResponseResponseEntity);
        return data;
    }
}
