package mentoring.epam.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transaction extends BaseTransaction{

    @Generated
    private String id;

    public Transaction(String user, Double amount) {
        super(user, amount);
    }
}

