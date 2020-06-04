package mentoring.epam.atm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="rabbitmq")
@Getter
@Setter
public class RabbitmqConfig2 {

    private String host;
    private int port;
    private String user;
    private String password;

}
