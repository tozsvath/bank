package mentoring.epam.rabbitmq.sender;

import mentoring.epam.bank.domain.Transaction;
import mentoring.epam.bank.response.TransactionResponse;
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
public class RabbitmqSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    public void send(ResponseEntity<TransactionResponse> transaction, String routeKey) {

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message(getBytes(transaction),messageProperties);

        template.convertAndSend(direct.getName(), routeKey, message);
    }

    private byte[] getBytes(ResponseEntity<TransactionResponse> transactionResponseResponseEntity) {

        byte[] data = SerializationUtils.serialize(transactionResponseResponseEntity);
        return data;
    }

}
