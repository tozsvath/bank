package mentoring.epam.bank.repository.rabbitmq;

import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqSenderBank {

    private RabbitTemplate template;

    private DirectExchange direct;

    @Autowired
    public RabbitmqSenderBank(RabbitTemplate template, DirectExchange direct) {
        this.template = template;
        this.direct = direct;
    }

    public void send(TransactionResponse transaction, String routeKey) {

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message(getBytes(transaction),messageProperties);

        template.convertAndSend(direct.getName(), routeKey, message);
    }

    private byte[] getBytes(TransactionResponse transactionResponseResponseEntity) {

        byte[] data = SerializationUtils.serialize(transactionResponseResponseEntity);
        return data;
    }

}
