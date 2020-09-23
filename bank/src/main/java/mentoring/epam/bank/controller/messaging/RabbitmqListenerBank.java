package mentoring.epam.bank.controller.messaging;

import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;
import mentoring.epam.bank.domain.bank.response.PaymentResponse;
import mentoring.epam.bank.repository.rabbitmq.config.BankToAtmChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@EnableBinding(BankToAtmChannel.class)
public class RabbitmqListenerBank {

    public static final String ATM_TO_BANK_NAME = "atmToBank";
    private final List<PaymentResponse> paymentResponses;

    public RabbitmqListenerBank(List<PaymentResponse> paymentResponses) {
        this.paymentResponses = paymentResponses;
    }

    @StreamListener(ATM_TO_BANK_NAME)
    public void atmToBank(@Payload Transaction tr) {

        try {
            paymentResponses.stream()
                    .filter(pay -> pay.getPaymentType().equals(tr.getPaymentType()))
                    .findFirst()
                    .orElseThrow(InvalidParameterException::new)
                    .execute(tr);
        } catch (InvalidPaymentTypeException e) {
            e.printStackTrace();
        }
    }
}
