package com.github.msemitkin.chat.server.rest;

import com.github.msemitkin.chat.server.dao.UserDao;
import com.github.msemitkin.chat.server.rest.model.UserData;
import com.github.msemitkin.chat.server.rest.model.request.CreateConversationRequest;
import com.github.msemitkin.chat.server.rest.model.request.LoginRequest;
import com.github.msemitkin.chat.server.rest.model.response.CreateConversationResponse;
import com.github.msemitkin.chat.server.rest.model.response.GetContactsResponse;
import com.github.msemitkin.chat.server.rest.model.response.GetConversationResponse;
import com.github.msemitkin.chat.server.rest.model.response.LoginResponse;
import com.github.msemitkin.chat.server.rest.model.response.RegisterResponse;
import com.github.msemitkin.chat.server.service.RegisterUserService;
import com.github.msemitkin.chat.server.utils.QueueHelper;
import com.github.msemitkin.chat.server.utils.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    private final UserDao userDao;
    private final QueueHelper queueHelper;
    private final RegisterUserService registerUserService;

    public ApplicationController(
        UserDao userDao,
        QueueHelper queueHelper,
        RegisterUserService registerUserService
    ) {
        this.userDao = userDao;
        this.queueHelper = queueHelper;
        this.registerUserService = registerUserService;
    }

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Integer userIdByName = userDao.getUserIdByName(loginRequest.getUsername());
        if (userIdByName == null) {
            UserData userData = new UserData();
            userData.setUsername(loginRequest.getUsername());
            userData.setPassword(loginRequest.getPassword());
            RegisterResponse register = registerUserService.registerUser(userData);
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

    @GetMapping("/api/users/{userName}/contacts")
    public GetContactsResponse getContacts(@PathVariable("userName") String userName) {
        List<String> contacts = userDao.getUserContacts(userName);
        return new GetContactsResponse(contacts);
    }

    @GetMapping("/api/conversations")
    public GetConversationResponse getConversation(
        @RequestParam("firstUser") String firstUser,
        @RequestParam("secondUser") String secondUser
    ) {
        Integer firstUserId = userDao.getUserIdByName(firstUser);
        Integer secondUserId = userDao.getUserIdByName(secondUser);
        String conversationName = userDao.getConversationByParticipantsId(firstUserId, secondUserId);
        return new GetConversationResponse(conversationName);
    }

    @PostMapping("/api/conversations")
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

    private Integer getUserIdByName(String firstUser) {
        return userDao.getUserIdByName(firstUser);
    }
}
