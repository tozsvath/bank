package mentoring.epam.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCredential {

    private String user;
    private String password;

}
