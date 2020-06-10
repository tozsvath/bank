package mentoring.epam.bank.rabbitmq.config;

import mentoring.epam.bank.commons.rabbitmq.RabbitmqQueueNames;
import mentoring.epam.bank.commons.rabbitmq.RabbitmqRouteNames;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;

@Configuration
public class Conf {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("transaction2");
    }

    @Bean
    public Queue atmToBank() {
        return new Queue(RabbitmqQueueNames.ATM_TO_BANK.name());
    }

    @Bean
    public Queue bankToAtm() {
        return new Queue(RabbitmqQueueNames.BANK_TO_ATM.name());
    }

    @Bean
    public Queue bankToAtmError() {
        return new Queue(RabbitmqQueueNames.BANK_TO_ATM_ERROR.name());
    }

    @Bean
    public Binding bindingDeposit(DirectExchange direct,
                                  Queue bankToAtm) {
        return BindingBuilder.bind(bankToAtm)
                .to(direct)
                .with(RabbitmqRouteNames.DEPOSIT.name());
    }

    @Bean
    public Binding bindingWithdraw(DirectExchange direct,
                                   Queue bankToAtm) {
        return BindingBuilder.bind(bankToAtm)
                .to(direct)
                .with(RabbitmqRouteNames.WITHDRAW.name());
    }

    @Bean
    public Binding bindingError(DirectExchange direct,
                                Queue bankToAtmError) {
        return BindingBuilder.bind(bankToAtmError)
                .to(direct)
                .with(RabbitmqRouteNames.ERROR.name());
    }
}