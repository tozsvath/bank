package mentoring.epam.bank.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {

    private String id;
    private String user;
    private Double balance;
    private String status;
}
