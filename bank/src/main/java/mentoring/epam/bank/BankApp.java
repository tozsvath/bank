package mentoring.epam.bank;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages={
        "mentoring.epam.bank.repository.keycloak.auth.auth", "mentoring.epam.bank","mentoring.epam.bank.rabbitmq.rabbitmq"})
public class BankApp {

    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
            System.out.println("end of beans ------ end of beans");
            
            Map<String, String> env = System.getenv();
            env.forEach((k, v) -> System.out.println(k + ":" + v));
        };

    }

}



