package mentoring.epam.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResponse {

    public String id;
    public String user;
    public Double amount;
    public String message;

}