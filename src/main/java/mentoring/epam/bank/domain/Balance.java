package mentoring.epam.bank.domain;

import org.springframework.data.annotation.Id;

public class Balance {
    @Id
    public String user;
    public double amount;

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
        return amount;
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
