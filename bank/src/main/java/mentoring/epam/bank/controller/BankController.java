package mentoring.epam.bank.controller;

import mentoring.epam.bank.auth.client.KeycloakClient;
import mentoring.epam.bank.domain.Balance;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.bank.commons.response.TransactionResponse;
import mentoring.epam.bank.domain.Transaction;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

@RestController
public class BankController {

    public static final String AUTHORIZATION = "Authorization";
    private Bank bank;

    private KeycloakClient authentication;

    public BankController(Bank bank, KeycloakClient authentication) {
        this.bank = bank;
        this.authentication = authentication;
    }

    @PostMapping("/validate")
    ResponseEntity<TokenIntrospectionResponse> validate(String token) throws AuthenticationException {

        try {
            return new ResponseEntity<>(authentication.validateToken(token), HttpStatus.OK);
        } catch (IOException e) {
            throw new AuthenticationException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/deposit")
    TransactionResponse depositCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.depositCash(transaction) : null;
    }

    @PostMapping("/balance")
    ResponseEntity<Balance> balance(@RequestHeader(AUTHORIZATION) String token, @RequestBody String username) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.getBalance(username) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/balance/all")
    ResponseEntity<List<Balance>> balance(@RequestHeader(AUTHORIZATION) String token) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.getAllBalance() : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/withdraw")
    TransactionResponse withdrawCash(@RequestHeader(AUTHORIZATION) String token, @RequestBody Transaction transaction) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.withdrawCash(transaction) : null;
    }
}