package mentoring.epam.bank.domain.bank.response;

import mentoring.epam.bank.commons.domain.bank.PaymentType;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;

public interface PaymentResponse {
    public void execute(Transaction tr) throws InvalidPaymentTypeException;
    public PaymentType getPaymentType();

}
