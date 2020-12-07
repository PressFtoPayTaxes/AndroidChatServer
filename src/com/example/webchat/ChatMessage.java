package com.example.webchat;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private Long id;
    private String author;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(Long id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
    }

    public ChatMessage(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
