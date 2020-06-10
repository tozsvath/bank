package mentoring.epam.atm.rabbitmq;

import mentoring.epam.bank.commons.response.TransactionResponse;
import mentoring.epam.bank.domain.Transaction;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqListenerAtm {
    public static final String bankToAtm = "#{bankToAtm.name}";
    public static final String bankToAtmError = "#{bankToAtmError.name}";


    @RabbitListener(queues = bankToAtm)
    public void withdraw(Message message) throws Exception {

        ResponseEntity<TransactionResponse>  responseResponseEntity = new ResponseEntity<>((TransactionResponse) SerializationUtils.deserialize(message.getBody()), HttpStatus.OK);
        //TODO what to give back?
    }

    @RabbitListener(queues = bankToAtmError)
    public void deposit(Message message) throws Exception {
        ResponseEntity<TransactionResponse> responseResponseEntity = new ResponseEntity<>((TransactionResponse) SerializationUtils.deserialize(message.getBody()), HttpStatus.OK);
        //TODO what to give back?
    }

}
