package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddHandler extends BaseTemplateHandler {

    List<User> list;

    public AddHandler(List<User> list) {
        this.list = list;
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        User user = new User(params().get("login"), params().get("password"));
        list.add(user);
        sendTemplate("Add.ftlh", Map.of("list", list));
    }
}
