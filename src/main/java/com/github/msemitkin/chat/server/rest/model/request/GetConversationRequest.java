package com.github.msemitkin.chat.server.rest.model.request;

@SuppressWarnings("unused")
public class GetConversationRequest {
    private String firstUser;
    private String secondUser;

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }
}
