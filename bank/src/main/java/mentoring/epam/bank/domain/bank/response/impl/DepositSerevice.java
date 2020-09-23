package mentoring.epam.bank.domain.bank.response.impl;

import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.BankService;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;
import mentoring.epam.bank.domain.bank.response.PaymentResponse;
import mentoring.epam.bank.repository.rabbitmq.RabbitmqSenderBank;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DepositSerevice implements PaymentResponse {


    private final BankService bank;
    private final RabbitTemplate template;

    public DepositSerevice(BankService bank, RabbitTemplate template) {
        this.bank = bank;
        this.template = template;
    }

    public void sendResponseToAtmWithdraw(RabbitmqSenderBank rabbitmqSenderBank, BankService bankService, Transaction tr) {

        TransactionResponse transactionResponse = null;
        try {
            transactionResponse = bankService.executeTransaction(tr);
        } catch (InvalidPaymentTypeException e) {
            e.printStackTrace();
        }
        rabbitmqSenderBank.sendWithdraw(transactionResponse);

    }

    public void sendResponseToAtmDeposit(RabbitmqSenderBank rabbitmqSenderBank, BankService bankService, Transaction tr) {

        TransactionResponse transactionResponse = null;
        try {
            transactionResponse = bankService.executeTransaction(tr);
        } catch (InvalidPaymentTypeException e) {
            e.printStackTrace();
        }
        rabbitmqSenderBank.sendDeposit(transactionResponse);

    }

    public void sendError(RabbitmqSenderBank rabbitmqSenderBank, BankService bankService, Transaction tr) {
        TransactionResponse transactionResponse = TransactionResponse.builder().message("Error, no transaction found.").build();
        rabbitmqSenderBank.sendError(transactionResponse);
    }

    @Override
    public void execute(Transaction tr){
        TransactionResponse transactionResponse = null;
        try {
            transactionResponse = bank.executeTransaction(tr);
        } catch (InvalidPaymentTypeException e) {
            e.printStackTrace();
        }
        sendResponseToAtm(transactionResponse);
    }

    private void sendResponseToAtm(TransactionResponse transactionResponse) {
        template.convertAndSend(transactionResponse);
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.DEPOSIT;
    }
}
