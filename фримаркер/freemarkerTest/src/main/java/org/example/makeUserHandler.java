package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class makeUserHandler extends BaseTemplateHandler {


    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        User user = new User(params().get("login"), params().get("password"));
        sendTemplate("makeUser.ftlh", Map.of("user", user));
    }
}
