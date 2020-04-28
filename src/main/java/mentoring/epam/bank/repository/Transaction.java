package mentoring.epam.bank.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@AllArgsConstructor
public class Transaction {
    @Id
    private String id;

    private String user;
    private Double amount;
}
