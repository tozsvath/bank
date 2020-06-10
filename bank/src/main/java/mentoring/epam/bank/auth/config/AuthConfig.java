package mentoring.epam.bank.auth.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
@NoArgsConstructor
@Getter
@Setter
public class AuthConfig {

    private String authServerUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

}
