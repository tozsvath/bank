package mentoring.epam.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BaseTransaction {
    private String user;
    private Double amount;
}
