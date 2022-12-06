package com.github.msemitkin.chat.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msemitkin.chat.server.broker.model.Message;

import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonMapper() {
    }

    public static Message getMessage(String jsonMessage) throws IOException {
        return mapper.readValue(jsonMessage, Message.class);
    }
}
