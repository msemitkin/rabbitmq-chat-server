package com.github.msemitkin.chat.server;

import com.github.msemitkin.chat.server.config.RabbitMqConfig;
import com.github.msemitkin.chat.server.rest.model.UserData;
import com.github.msemitkin.chat.server.service.RegisterUserService;
import com.github.msemitkin.chat.server.utils.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitMqConfig.class)
public class Application implements CommandLineRunner {
    private final RegisterUserService registerUserService;

    public Application(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) {
        registerUserService.registerUser(new UserData("user1@foo.bar", "user1", StringUtils.getRandomString(7)));
        registerUserService.registerUser(new UserData("user2@foo.bar", "user2", StringUtils.getRandomString(7)));
    }
}
