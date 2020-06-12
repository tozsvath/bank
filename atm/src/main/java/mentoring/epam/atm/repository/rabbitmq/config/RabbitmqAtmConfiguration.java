package mentoring.epam.atm.repository.rabbitmq.config;

import mentoring.epam.bank.commons.repository.rabbitmq.RabbitmqQueueNames;
import mentoring.epam.bank.commons.repository.rabbitmq.RabbitmqRouteNames;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqAtmConfiguration {

    @Bean
        public DirectExchange direct() {
        return new DirectExchange("transaction");
    }

    private static class ReceiverConfig {

        @Bean
        public Queue bankToAtm() {
            return new Queue(RabbitmqQueueNames.BANK_TO_ATM.name());
        }

        @Bean
        public Queue bankToAtmError() {
            return new Queue(RabbitmqQueueNames.BANK_TO_ATM_ERROR.name());
        }

        @Bean
        public Queue atmToBank() {
            return new Queue(RabbitmqQueueNames.ATM_TO_BANK.name());
        }

        @Bean
        public Binding binding1a(DirectExchange direct,
                                 Queue bankToAtm) {
            return BindingBuilder.bind(bankToAtm)
                    .to(direct)
                    .with(RabbitmqRouteNames.WITHDRAW.name());
        }

        @Bean
        public Binding binding1b(DirectExchange direct,
                                 Queue atmToBank) {
            return BindingBuilder.bind(atmToBank)
                    .to(direct)
                    .with(RabbitmqRouteNames.DEPOSIT.name());
        }
    }
}