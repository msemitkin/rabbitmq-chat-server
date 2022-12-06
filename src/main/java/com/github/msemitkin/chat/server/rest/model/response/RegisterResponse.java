package com.github.msemitkin.chat.server.rest.model.response;

public class RegisterResponse {
    private final String exchange;

    public RegisterResponse(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }
}
