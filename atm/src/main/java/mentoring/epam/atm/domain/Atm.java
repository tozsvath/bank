package mentoring.epam.atm.domain;

import org.springframework.http.ResponseEntity;

public class Atm {

    private double cash;

    public ResponseEntity depositCash(String user, int amount) {
        ResponseEntity entity = ResponseEntity.ok().build();

        return entity;
    }

    public ResponseEntity withdrawCash(String user, int amount) {
        ResponseEntity entity = ResponseEntity.ok().build();

        return entity;
    }

}
