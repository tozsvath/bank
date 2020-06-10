package mentoring.epam.atm.domain;

import java.util.Objects;

public class Balance {

    public static final int NO_VALUE = 0;
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
        return Objects.isNull(amount) ? NO_VALUE : amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
