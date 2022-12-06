package com.github.msemitkin.chat.server.broker;

import com.github.msemitkin.chat.server.broker.model.Message;
import com.github.msemitkin.chat.server.utils.Constants;
import com.github.msemitkin.chat.server.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private final MessageSender sender;

    public MessageListener(MessageSender sender) {
        this.sender = sender;
    }

    @RabbitListener(queues = Constants.CHAT_APPLICATION_QUEUE)
    public void receiveMessageFromClient(byte[] body) throws IOException {
        String jsonMessage = new String(body, StandardCharsets.UTF_8);
        logger.info("Message received: {}", jsonMessage);
        Message message = JsonMapper.getMessage(jsonMessage);
        sender.sendMessage(message.getConversation(), jsonMessage);
    }

}
