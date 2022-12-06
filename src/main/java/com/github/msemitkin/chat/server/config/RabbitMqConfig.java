package com.github.msemitkin.chat.server.config;

import com.github.msemitkin.chat.server.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue incomingMessegesQueue() {
        return new Queue(Constants.CHAT_APPLICATION_QUEUE, false);
    }

    @Bean
    public FanoutExchange outgoingExchange() {
        return new FanoutExchange(Constants.CONVERSATION_OUTGOING_EXHANGE);
    }

    @Bean
    public Binding bindingIncomingMessageQueue(
        FanoutExchange outgoingExchange,
        Queue incomingMessegesQueue
    ) {
        return BindingBuilder
            .bind(incomingMessegesQueue)
            .to(outgoingExchange);
    }

    @Bean
    public TopicExchange incomingExchange() {
        return new TopicExchange(Constants.CONVERSTATION_INCOMING_EXCHANGE);
    }
}
