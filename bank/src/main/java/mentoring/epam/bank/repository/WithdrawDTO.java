package mentoring.epam.bank.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import mentoring.epam.auth.client.KeycloakClient;
import mentoring.epam.bank.domain.Transaction;

@AllArgsConstructor
@Getter
@Setter
public class WithdrawDTO {
    private Transaction transaction;
    private String token;

}
