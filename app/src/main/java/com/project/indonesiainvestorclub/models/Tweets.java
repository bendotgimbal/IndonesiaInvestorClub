package com.project.indonesiainvestorclub.models;

public class Tweets {
    private String author;
    private String content;
    private String date;

    int index;

    public Tweets(String author, String content, String date) {
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
