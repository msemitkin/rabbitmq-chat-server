package com.github.msemitkin.chat.server.dao;

import com.github.msemitkin.chat.server.rest.model.Conversation;
import com.github.msemitkin.chat.server.rest.model.UserData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InMemoryDao implements UserDao {
    private final List<UserData> users = new ArrayList<>();
    private final List<Conversation> conversations = new ArrayList<>();

    @Override
    public void createNewUser(UserData newUser) {
        int id = users.size();
        newUser.setId(id);
        users.add(newUser);
    }

    @Override
    public String getUserPassword(String username) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username))
            .map(UserData::getPassword)
            .findAny()
            .orElse(null);
    }

    @Override
    public String getUserExchange(String username) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username))
            .map(UserData::getExchange)
            .findAny()
            .orElse(null);
    }

    @Override
    public List<String> getUserContacts(String username) {
        return users.stream()
            .map(UserData::getUsername)
            .filter(userUsername -> !userUsername.equals(username))
            .collect(Collectors.toList());
    }

    @Override
    public void createNewConversation(String newConversationName) {
        Conversation conversation = new Conversation(Objects.requireNonNull(newConversationName));
        conversations.add(conversation);
    }

    @Override
    public String getConversationByParticipantsId(int firstUser, int secondUser) {
        String first = getUserNameById(firstUser);
        String second = getUserNameById(secondUser);
        HashSet<String> users = new HashSet<>();
        users.add(first);
        users.add(second);
        return conversations.stream()
            .filter(conversation -> new HashSet<>(conversation.getUserNames()).containsAll(users))
            .map(Conversation::getName)
            .findAny()
            .orElse(null);
    }

    @Override
    public void addUserToConversation(String conversationName, Integer userId) {
        String userName = getUserNameById(userId);
        getConversationByName(conversationName).addUserName(userName);
    }

    @Override
    public Integer getUserIdByName(String firstUser) {
        return users.stream().filter(user -> user.getUsername().equals(firstUser))
            .map(UserData::getId)
            .findAny()
            .orElse(null);
    }

    private String getUserNameById(int id) {
        return users.stream()
            .filter(user -> user.getId() == id)
            .map(UserData::getUsername)
            .findAny()
            .orElse(null);
    }

    private Conversation getConversationByName(String conversationName) {
        return conversations.stream()
            .filter(conversation -> conversation.getName().equals(conversationName))
            .findAny()
            .orElse(null);
    }
}
