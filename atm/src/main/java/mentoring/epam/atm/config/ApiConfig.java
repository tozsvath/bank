package mentoring.epam.atm.config;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="api")
@Getter
@Setter
public class ApiConfig {

    private String host;
    private String port;
}
