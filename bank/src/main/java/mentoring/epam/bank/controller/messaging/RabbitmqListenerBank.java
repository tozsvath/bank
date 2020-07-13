package mentoring.epam.bank.controller.messaging;

import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.repository.rabbitmq.RabbitmqRouteNames;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.Bank;
import mentoring.epam.bank.repository.rabbitmq.RabbitmqSenderBank;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RabbitmqListenerBank {

    public static final String ATM_TO_BANK_NAME = "#{atmToBank.name}";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    private final RabbitmqSenderBank rabbitmqSenderBank;
    private final Bank bank;

    public RabbitmqListenerBank(RabbitmqSenderBank rabbitmqSenderBank, Bank bank) {
        this.rabbitmqSenderBank = rabbitmqSenderBank;
        this.bank = bank;
    }

    @RabbitListener(queues = ATM_TO_BANK_NAME)
    public void atmToBank(Message message) throws Exception {
        String routeKey = message.getMessageProperties().getReceivedRoutingKey();

        //TODO STRATEGY pattern, remove enum -> 2 class interfacebol

        if (WITHDRAW.equals(routeKey)) {

            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());
            TransactionResponse transactionResponse = bank.withdraw(transaction);
            rabbitmqSenderBank.send(transactionResponse, RabbitmqRouteNames.WITHDRAW.name());

            TransactionResponse responseResponseEntity = transactionResponse;

            if (!Objects.isNull(responseResponseEntity)) {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.DEPOSIT.name());
            } else {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.ERROR.name());
            }
        } else if (DEPOSIT.equals(routeKey)) {

            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());

            TransactionResponse responseResponseEntity = bank.deposit(transaction);

            if (!Objects.isNull(responseResponseEntity)) {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.DEPOSIT.name());
            } else {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.ERROR.name());
            }
        }
    }
}
