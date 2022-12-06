package com.github.msemitkin.chat.server.rest;

import com.github.msemitkin.chat.server.rest.model.UserData;
import com.github.msemitkin.chat.server.rest.model.request.CreateConversationRequest;
import com.github.msemitkin.chat.server.rest.model.request.GetContactsRequest;
import com.github.msemitkin.chat.server.rest.model.request.GetConversationRequest;
import com.github.msemitkin.chat.server.rest.model.request.LoginRequest;
import com.github.msemitkin.chat.server.rest.model.response.CreateConversationResponse;
import com.github.msemitkin.chat.server.rest.model.response.GetContactsResponse;
import com.github.msemitkin.chat.server.rest.model.response.GetConversationResponse;
import com.github.msemitkin.chat.server.rest.model.response.LoginResponse;
import com.github.msemitkin.chat.server.rest.model.response.RegisterResponse;
import com.github.msemitkin.chat.server.service.RegisterUserService;
import com.github.msemitkin.chat.server.utils.QueueHelper;
import com.github.msemitkin.chat.server.utils.StringUtils;
import com.github.msemitkin.chat.server.dao.UserDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiMethods {

    private final UserDao userDao;
    private final QueueHelper queueHelper;
    private final RegisterUserService registerUserService;

    public ApiMethods(UserDao userDao, QueueHelper queueHelper, RegisterUserService registerUserService) {
        this.userDao = userDao;
        this.queueHelper = queueHelper;
        this.registerUserService = registerUserService;
    }

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String userIdByName = userDao.getUserIdByName(loginRequest.getUsername());
        if (userIdByName == null) {
            UserData userData = new UserData();
            userData.setUsername(loginRequest.getUsername());
            userData.setPassword(loginRequest.getPassword());
            RegisterResponse register = register(userData);
            return new LoginResponse(register.getExchange());
        }
        String password = userDao.getUserPassword(loginRequest.getUsername());
        final String exchange;
        if (!password.equals(loginRequest.getPassword())) {
            exchange = "Wrong username or password";
        } else {
            exchange = userDao.getUserExchange(loginRequest.getUsername());
        }
        return new LoginResponse(exchange);
    }

    @PostMapping("/api/register")
    public RegisterResponse register(@RequestBody UserData newUser) {
        return registerUserService.registerUser(newUser);
    }

    @PostMapping("/api/contacts")
    public GetContactsResponse getContacts(@RequestBody GetContactsRequest getContactsRequest) {
        List<String> contacts = userDao.getUserContacts(getContactsRequest.getUsername());
        return new GetContactsResponse(contacts);
    }

    @PostMapping("/api/conversation")
    public GetConversationResponse getConversation(@RequestBody GetConversationRequest conversationRequest) {
        String firstUserId = userDao.getUserIdByName(conversationRequest.getFirstUser());
        String secondUserId = userDao.getUserIdByName(conversationRequest.getSecondUser());
        String conversationName = userDao.getConversationByParticipantsId(Integer.parseInt(firstUserId), Integer.parseInt(secondUserId));
        return new GetConversationResponse(conversationName);
    }

    @PostMapping("/api/conversation/create")
    public CreateConversationResponse createConversation(@RequestBody CreateConversationRequest createConversationRequest) {
        String newConversationName = StringUtils.getRandomString(8);

        userDao.createNewConversation(newConversationName);
        userDao.addUserToConversation(newConversationName, getUserIdByName(createConversationRequest.getFirstUser()));
        userDao.addUserToConversation(newConversationName, getUserIdByName(createConversationRequest.getSecondUser()));
        queueHelper.bindExchangeByConversationName(
            userDao.getUserExchange(createConversationRequest.getFirstUser()), newConversationName);
        queueHelper.bindExchangeByConversationName(
            userDao.getUserExchange(createConversationRequest.getSecondUser()),
            newConversationName);
        return new CreateConversationResponse(newConversationName);
    }

    private String getUserIdByName(String firstUser) {
        return userDao.getUserIdByName(firstUser);
    }
}
