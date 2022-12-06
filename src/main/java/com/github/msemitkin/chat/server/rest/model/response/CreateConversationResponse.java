package com.github.msemitkin.chat.server.rest.model.response;

@SuppressWarnings("unused")
public class CreateConversationResponse {
    private final String conversationName;

    public CreateConversationResponse(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }
}
