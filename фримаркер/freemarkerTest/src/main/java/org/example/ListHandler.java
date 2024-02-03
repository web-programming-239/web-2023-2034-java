package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListHandler extends BaseTemplateHandler {

    List<User> list;
    public ListHandler(List<User> list) {
        this.list = list;
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        Map<String, String> p = params();

        sendTemplate("list.ftlh", Map.of("list", list, "user", params().get("user"), "msg", params().get("msg")));
    }
}
