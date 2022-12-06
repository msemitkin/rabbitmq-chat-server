package com.github.msemitkin.chat.server.rest.model.response;

@SuppressWarnings("unused")
public class LoginResponse {
    private final String exchange;

    public LoginResponse(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }
}
