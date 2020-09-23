package mentoring.epam.bank.domain.bank.response.impl;

import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import mentoring.epam.bank.domain.bank.BankService;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;
import mentoring.epam.bank.domain.bank.response.PaymentResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService implements PaymentResponse {
    private final BankService bank;
    private final RabbitTemplate template;

    public WithdrawService(BankService bank, RabbitTemplate template) {
        this.bank = bank;
        this.template = template;
    }

    @Override
    public void execute(Transaction tr) throws InvalidPaymentTypeException {

        TransactionResponse transactionResponse = bank.executeTransaction(tr);
        sendResponseToAtm(transactionResponse);
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.WITHDRAW;
    }

    private void sendResponseToAtm(TransactionResponse transactionResponse) {
        template.convertAndSend(transactionResponse);
    }



}
