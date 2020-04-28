package mentoring.epam.bank;

import java.util.Arrays;


import mentoring.epam.bank.domain.Balance;
import mentoring.epam.bank.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BankApp implements CommandLineRunner {

    @Autowired
    private BalanceRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                //System.out.println(beanName);
            }

        };

    }


    @Override
    public void run(String... args) throws Exception {

        //repository.deleteAll();

        // save a couple of customers
        //repository.save(new Balance("Alice", 100));
        //repository.save(new Balance("Bob", 101));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Balance customer : repository.findAll()) {
            System.out.println(customer.user +" -- " +customer.amount);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUser("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");

    }

}



