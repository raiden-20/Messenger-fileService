package ru.vsu.cs.sheina.fileservice.configuration.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue socialServiceQueue() {
        return new Queue(RabbitQueues.toSocialQueue);
    }

    @Bean
    public Queue blogServiceQueue() {
        return new Queue(RabbitQueues.toBlogQueue);
    }
}
