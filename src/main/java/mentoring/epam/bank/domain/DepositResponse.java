package mentoring.epam.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class DepositResponse {

    @Id
    private String id;
    private String status;
}
