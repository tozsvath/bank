package mentoring.epam.atm.rabbitmq;

import mentoring.epam.auth.client.KeycloakClient;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.bank.domain.Transaction;
import mentoring.epam.bank.response.TransactionResponse;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


public class RabbitmqListener {

    @Autowired
    RabbitmqSender rabbitmqSender;

    @Autowired
    private KeycloakClient authentication;

    @Autowired
    private Bank bank;

    @RabbitListener(queues = "#{bankToAtm.name}")
    public void withdraw(Message message) throws Exception {


    }

    @RabbitListener(queues = "#{bankToAtmError.name}")
    public void deposit(Message message) throws Exception {

    }

}
