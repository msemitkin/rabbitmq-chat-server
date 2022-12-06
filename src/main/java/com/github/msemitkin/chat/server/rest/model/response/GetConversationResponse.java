package com.github.msemitkin.chat.server.rest.model.response;

@SuppressWarnings("unused")
public class GetConversationResponse {
    private final String conversationName;

    public GetConversationResponse(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }
}
