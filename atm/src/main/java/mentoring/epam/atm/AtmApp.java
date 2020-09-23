package mentoring.epam.atm;

import mentoring.epam.atm.repository.rabbitmq.TransactionMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TransactionMessaging.class)
@SpringBootApplication(scanBasePackages={"mentoring.epam.atm","mentoring.epam.bank.rabbitmq.rabbitmq"})
public class AtmApp {

    public static void main(String[] args) {
        SpringApplication.run(AtmApp.class, args);
    }

}