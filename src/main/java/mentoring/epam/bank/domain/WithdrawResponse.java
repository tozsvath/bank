package mentoring.epam.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class WithdrawResponse {

    public String id;
    public String user;
    public Double amount;
    public String message;

    public WithdrawResponse(String id, String user, Double amount, String message) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.message = message;
    }
}
