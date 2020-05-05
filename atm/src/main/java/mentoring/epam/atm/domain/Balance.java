package mentoring.epam.atm.domain;

import java.util.Objects;

public class Balance {

    public String user;
    public Double amount;

    public Balance(String user, double amount) {
        this.user = user;
        this.amount = amount;
    }
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

}
