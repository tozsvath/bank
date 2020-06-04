package mentoring.epam.auth.client;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize
public class TokenValidationRequest {

    private String client_id;
    private String client_secret;
    private String token;

}

