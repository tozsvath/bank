package mentoring.epam.bank.domain.bank;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import mentoring.epam.bank.commons.domain.bank.Balance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final List<PaymentMethod> paymentMethods;
    private final BalanceRepository balanceRepository;

        public BankService(List<PaymentMethod> paymentMethods, BalanceRepository balanceRepository) {
        this.paymentMethods = paymentMethods;
        this.balanceRepository = balanceRepository;
    }

    public TransactionResponse executeTransaction(Transaction transaction) throws InvalidPaymentTypeException {
        return paymentMethods.stream()
                .filter(pay -> pay.getPaymentType().equals(transaction.getPaymentType()))
                .findFirst()
                .orElseThrow(() -> new InvalidPaymentTypeException("Invalid Payment type: " +transaction.getPaymentType()))
                .executeTransactionForPaymentMethod(transaction);
    }
    
    public ResponseEntity<Balance> getBalance(String username) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        Optional<Balance> balance = null;

        try {
            balance = balanceRepository.findByUser(username);

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(balance.get(), headers, httpStatus);
    }

    public ResponseEntity<List<Balance>> getAllBalance() {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        List<Balance> balance = null;

        try {
            balance = balanceRepository.findAll();

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(balance, headers, httpStatus);
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
}
