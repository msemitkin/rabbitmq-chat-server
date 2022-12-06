package com.github.msemitkin.chat.server.rest.model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private final String name;
    private final List<String> userNames;

    public Conversation(String name) {
        this.name = name;
        this.userNames = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void addUserName(String userName) {
        this.userNames.add(userName);
    }
}
