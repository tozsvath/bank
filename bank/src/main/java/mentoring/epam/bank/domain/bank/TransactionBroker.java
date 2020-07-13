package mentoring.epam.bank.domain.bank;

import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionBroker {

    public TransactionResponse executeTransaction(Payment payment){
       return payment.executeTransaction();
    }

}
