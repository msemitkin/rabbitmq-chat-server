package com.github.msemitkin.chat.server.utils;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

@Component
public class QueueHelper {
    private final RabbitAdmin admin;
    private final TopicExchange incomingExchange;

    public QueueHelper(
        RabbitAdmin admin,
        TopicExchange incomingExchange
    ) {
        this.admin = admin;
        this.incomingExchange = incomingExchange;
    }

    public void createExchange(String exchangeName) {
        FanoutExchange exchange = new FanoutExchange(exchangeName);
        admin.declareExchange(exchange);
    }

    public void bindExchangeByConversationName(String exchange, String conversationName) {
        Binding binding = BindingBuilder
            .bind(new FanoutExchange(exchange.replaceAll("\\s+", "")))
            .to(incomingExchange)
            .with(conversationName);
        admin.declareBinding(binding);
    }
}
