package com.github.msemitkin.chat.server.rest.model.request;

@SuppressWarnings("unused")
public class GetContactsRequest {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
