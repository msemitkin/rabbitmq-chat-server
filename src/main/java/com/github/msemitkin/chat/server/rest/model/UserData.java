package com.github.msemitkin.chat.server.rest.model;

public class UserData {
    private int id;
    private String username;
    private String password;
    private String exchange;

    public UserData() {
    }

    public UserData(
        String username,
        String password,
        String exchange
    ) {
        this.username = username;
        this.password = password;
        this.exchange = exchange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
