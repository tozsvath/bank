package mentoring.epam.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenValidation {

        @JsonProperty("active")
        Boolean active;

}
