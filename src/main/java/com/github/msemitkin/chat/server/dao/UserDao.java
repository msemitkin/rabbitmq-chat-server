package com.github.msemitkin.chat.server.dao;

import com.github.msemitkin.chat.server.rest.model.UserData;

import java.util.List;

public interface UserDao {
    void createNewUser(UserData newUser);

    String getUserPassword(String username);

    String getUserExchange(String username);

    List<String> getUserContacts(String username);

    void createNewConversation(String newConversationName);

    String getConversationByParticipantsId(int firstUser, int secondUser);

    void addUserToConversation(String conversationName, Integer userId);

    Integer getUserIdByName(String firstUser);

}
