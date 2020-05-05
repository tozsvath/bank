package mentoring.epam.bank.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

public class Balance {

    @Id
    String _id;

    public String user;
    public Double amount;

    public Balance(String user, double amount) {
        this.user = user;
        this.amount = amount;
    }

    public Balance(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return Objects.isNull(amount) ? 0 : amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void depositCash(double amount) {
        this.amount += amount;
    }

    public void withdrawCash(double amount) {
        this.amount -= amount;
    }
}
