package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NameHandler extends BaseTemplateHandler {

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        Map<String, String> p = params();

        sendTemplate("name.ftlh", Map.of("name",params().get("name")));
    }
}
