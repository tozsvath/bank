package mentoring.epam.bank.controller;

import mentoring.epam.auth.client.KeycloakClient;
import mentoring.epam.auth.domain.TokenValidation;
import mentoring.epam.bank.domain.Balance;
import mentoring.epam.bank.domain.Bank;
import mentoring.epam.auth.domain.UserCredential;
import mentoring.epam.bank.repository.WithdrawDTO;
import mentoring.epam.bank.response.DepositResponse;
import mentoring.epam.bank.response.WithdrawResponse;
import mentoring.epam.bank.domain.Transaction;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

@RestController
public class BankController {

    @Autowired
    private Bank bank;

    @Autowired
    private KeycloakClient authentication;

    @PostMapping("/validate")
    ResponseEntity<TokenIntrospectionResponse> validate(String token) throws AuthenticationException {

        try {
            return new ResponseEntity<>(authentication.validateToken(token), HttpStatus.OK);
        } catch (IOException e) {
            throw new AuthenticationException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/deposit")
    ResponseEntity<DepositResponse> depositCash(@RequestHeader("Authorization") String token, @RequestBody Transaction transaction) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.depositCash(transaction) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/balance")
    ResponseEntity<Balance> balance(@RequestHeader("Authorization") String token, @RequestBody String username) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.getBalance(username) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/balance/all")
    ResponseEntity<List<Balance>> balance(@RequestHeader("Authorization") String token) throws AuthenticationException {

        return validate(token).getBody().getActive().booleanValue() ? bank.getAllBalance() : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/withdraw")
    ResponseEntity<WithdrawResponse> withdrawCash(@RequestHeader("Authorization") String token, @RequestBody Transaction transaction) throws AuthenticationException {


        return validate(token).getBody().getActive().booleanValue() ? bank.withdrawCash(transaction) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}