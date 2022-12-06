package com.github.msemitkin.chat.server.service;

import com.github.msemitkin.chat.server.dao.UserDao;
import com.github.msemitkin.chat.server.rest.model.UserData;
import com.github.msemitkin.chat.server.rest.model.response.RegisterResponse;
import com.github.msemitkin.chat.server.utils.QueueHelper;
import com.github.msemitkin.chat.server.utils.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    private final QueueHelper queueHelper;
    private final UserDao userDao;

    public RegisterUserService(QueueHelper queueHelper, UserDao userDao) {
        this.queueHelper = queueHelper;
        this.userDao = userDao;
    }

    public RegisterResponse registerUser(UserData newUser) {
        String exchangeName = StringUtils.getRandomString(7);
        queueHelper.createExchange(exchangeName);
        newUser.setExchange(exchangeName);
        userDao.createNewUser(newUser);
        return new RegisterResponse(exchangeName);
    }
}
