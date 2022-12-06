package com.github.msemitkin.chat.server.broker;

import com.github.msemitkin.chat.server.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    private final RabbitTemplate template;

    public MessageSender(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(String conversation, String jsonMessage) {
        template.convertAndSend(
            Constants.CONVERSTATION_INCOMING_EXCHANGE,
            conversation,
            jsonMessage
        );
        logger.info("Sent message {} to conversation {}", jsonMessage, conversation);
    }
}
