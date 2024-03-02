package org.example;

public class User {
    String login;
    String password;
    public User (String l, String p){
        login = l;
        password = p;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
