package io.github.linarkou.jackson.spring.testdata;

import io.github.linarkou.jackson.JsonExclude;

public class User {
    private String username;
    @JsonExclude(ExcludePassword.class)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
