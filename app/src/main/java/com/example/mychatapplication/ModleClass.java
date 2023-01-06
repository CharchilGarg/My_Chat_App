package com.example.mychatapplication;

public class ModleClass {

    String message;
    String from;

    public ModleClass() {
    }

    public ModleClass(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
