package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        SimpleHttpServer server = new SimpleHttpServer();
        server.createContext("/list", new ListHandler(List.of(new User("1", "2"), new User("3", "4"))));
        server.createContext("/name", new NameHandler());
        server.createContext("/makeUser", new makeUserHandler());
        server.createContext("/add", new AddHandler(new ArrayList<>()));

        server.run();
    }
}
