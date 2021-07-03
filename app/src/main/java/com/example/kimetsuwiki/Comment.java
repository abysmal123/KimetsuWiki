package com.example.kimetsuwiki;

public class Comment {
    private String text;
    private String date;

    public Comment() {
        this.text = "null";
        this.date = "yyyy/mm/dd hh:mm:ss";
    }

    public Comment(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public String getDate() {
        return this.date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
