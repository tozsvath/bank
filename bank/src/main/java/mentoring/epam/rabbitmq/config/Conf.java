package mentoring.epam.rabbitmq.config;

import mentoring.epam.rabbitmq.listener.RabbitmqListener;
import mentoring.epam.rabbitmq.sender.RabbitmqSender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.*;

@Configuration
public class Conf {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("response");
    }

    @Bean
    public Queue hello() {
        return new Queue("atmToBank");
    }

    private static class ReceiverConfig {


        @Bean
        public RabbitmqListener receiver() {
            return new RabbitmqListener();
        }
    }

    @Bean
    public RabbitmqSender sender() {
        return new RabbitmqSender();
    }

}