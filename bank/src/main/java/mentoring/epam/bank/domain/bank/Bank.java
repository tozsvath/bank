package mentoring.epam.bank.domain.bank;

import com.mongodb.MongoException;
import mentoring.epam.bank.commons.domain.bank.Transaction;
import mentoring.epam.bank.domain.bank.paymentImpl.Deposit;
import mentoring.epam.bank.domain.bank.paymentImpl.Withdraw;
import mentoring.epam.bank.repository.mongodb.BalanceRepository;
import mentoring.epam.bank.commons.domain.bank.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import mentoring.epam.bank.commons.domain.bank.Balance;

import java.util.List;

@Component
public class Bank {

    private final BalanceRepository balanceRepository;

    @Autowired
    public Bank(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public TransactionResponse withdraw(Transaction transaction){

        Withdraw withdraw = new Withdraw(balanceRepository,transaction);
        return withdraw.executeTransaction();
    }

    public TransactionResponse deposit(Transaction transaction){
        Deposit deposit = new Deposit(balanceRepository,transaction);
        return deposit.executeTransaction();
    }

    public ResponseEntity<Balance> getBalance(String username) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        Balance balance = null;

        try {
            balance = balanceRepository.findByUser(username);

        } catch (MongoException mongoException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(balance, headers, httpStatus);
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
}
