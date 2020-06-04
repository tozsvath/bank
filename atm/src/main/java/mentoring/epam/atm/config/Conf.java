package mentoring.epam.atm.config;

import mentoring.epam.rabbitmq.listener.RabbitmqListener;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("transaction");
    }

    private static class ReceiverConfig {

            @Bean
            public Queue bankToAtm() {
                return new AnonymousQueue();
            }

            @Bean
            public Queue bankToAtmError() {
                return new AnonymousQueue();
            }

            @Bean
            public Binding withdrawBinding(DirectExchange direct,
                                     Queue bankToAtm) {
                return BindingBuilder.bind(bankToAtm)
                        .to(direct)
                        .with("withdraw");
            }

        @Bean
        public Binding depositBindig(DirectExchange direct,
                                 Queue bankToAtm) {
            return BindingBuilder.bind(bankToAtm)
                    .to(direct)
                    .with("deposit");
        }

            @Bean
            public Binding errorBinding(DirectExchange direct,
                                     Queue bankToAtmError) {
                return BindingBuilder.bind(bankToAtmError)
                        .to(direct)
                        .with("error");
            }
        }

        @Bean
        public RabbitmqListener receiver() {
            return new RabbitmqListener();
        }
    }