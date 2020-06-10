package mentoring.epam.bank.rabbitmq;

import mentoring.epam.bank.commons.rabbitmq.RabbitmqRouteNames;
import mentoring.epam.bank.commons.response.TransactionResponse;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.bank.domain.Transaction;
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

        if (WITHDRAW.equals(routeKey)) {

            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());
            rabbitmqSenderBank.send(bank.withdrawCash(transaction), RabbitmqRouteNames.WITHDRAW.name());

            TransactionResponse responseResponseEntity = bank.withdrawCash(transaction);

            if (!Objects.isNull(responseResponseEntity)) {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.DEPOSIT.name());
            } else {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.ERROR.name());
            }
        } else if (DEPOSIT.equals(routeKey)) {

            Transaction transaction = (Transaction) SerializationUtils.deserialize(message.getBody());
            TransactionResponse responseResponseEntity = bank.depositCash(transaction);

            if (!Objects.isNull(responseResponseEntity)) {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.DEPOSIT.name());
            } else {
                rabbitmqSenderBank.send(responseResponseEntity, RabbitmqRouteNames.ERROR.name());
            }
        }
    }
}
