package com.example.webchat;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String login;
    private String password;
    private String nickname;
    private boolean isOnline;

    public User() {
    }

    public User(Long id, String login, String password, String nickname, boolean isOnline) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
        this.isOnline = isOnline;
    }

    public User(String login, String password, String nickname, boolean isOnline) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
        this.isOnline = isOnline;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }


}
