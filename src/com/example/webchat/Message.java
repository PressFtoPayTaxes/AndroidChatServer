package com.example.webchat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    private Methods method;
    private String login;
    private String password;
    private String nickname;
    private String text;
    private ArrayList<User> onlineUsers;
    private User loginedUser;
    private ChatMessage newMessage;
    private ArrayList<ChatMessage> newMessages;
    private Long fromId;

    public Message() {
    }

    public Message(ArrayList<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public Message(User loginedUser) {
        this.loginedUser = loginedUser;
    }

    public Message(Methods method) {
        this.method = method;
    }

    public Message(Methods method, Long fromId) {
        this.method = method;
        this.fromId = fromId;
    }

    public Message(Methods method, String loginOrNickname, String passwordOrText) {
        this.method = method;

        if (method == Methods.METHOD_LOGIN){
            this.login = loginOrNickname;
            this.password = passwordOrText;
        }
        else if (method == Methods.METHOD_SEND_MESSAGE){
            this.nickname = loginOrNickname;
            this.text = passwordOrText;
        }
    }

    public Message(Methods method, String login, String password, String nickname) {
        this.method = method;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public Message(Methods method, ChatMessage newMessage) {
        this.method = method;
        this.newMessage = newMessage;
    }

    public Message(List<ChatMessage> newMessages) {
        this.newMessages = (ArrayList<ChatMessage>) newMessages;
    }

    public Methods getMethod() {
        return method;
    }

    public void setMethod(Methods method) {
        this.method = method;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(User loginedUser) {
        this.loginedUser = loginedUser;
    }

    public ChatMessage getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(ChatMessage newMessage) {
        this.newMessage = newMessage;
    }

    public ArrayList<ChatMessage> getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(ArrayList<ChatMessage> newMessages) {
        this.newMessages = newMessages;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
}
