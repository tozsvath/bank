package mentoring.epam.rabbitmq.listener;

import mentoring.epam.auth.client.KeycloakClient;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.bank.domain.Transaction;
import mentoring.epam.rabbitmq.sender.RabbitmqSender;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class RabbitmqListener {
    @Autowired
    private KeycloakClient authentication;

    @Autowired
    RabbitmqSender rabbitmqSender;

    @Autowired
    private Bank bank;

    @RabbitListener(queues = "#{atmToBank.name}")
    public void atmToBank(Message message) throws Exception {
        String routeKey = message.getMessageProperties().getReceivedRoutingKey();

        if ("withdraw".equals(routeKey)) {
            TokenIntrospectionResponse tokenValidation = authentication.validateToken(((message.getMessageProperties().getHeader("token").toString().replace("Bearer ", ""))));

            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());
            if (tokenValidation.getActive().booleanValue()) {
                rabbitmqSender.send(bank.withdrawCash(transaction),"withdraw");
            } else {
                throw new Exception();
            }
            //TODO

        } else if ("deposit".equals(routeKey)){

            TokenIntrospectionResponse tokenValidation = authentication.validateToken(((message.getMessageProperties().getHeader("token").toString().replace("Bearer ",""))));
            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());
            if (tokenValidation.getActive().booleanValue()) {
                rabbitmqSender.send(bank.depositCash(transaction),"deposit");

            } else {
                throw new Exception();
            }
            //TODO
        }
    }
}
