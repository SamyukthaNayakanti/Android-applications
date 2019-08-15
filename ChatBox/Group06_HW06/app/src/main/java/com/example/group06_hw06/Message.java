package com.example.group06_hw06;

import java.util.Date;

public class Message {
    String message;
    String name,key;
    String imageURL=null;
    Date date;

    public Message(String message, String name, String imageURL, String key, Date date) {
        this.message = message;
        this.name = name;
        this.imageURL = imageURL;
        this.key = key;
        this.date = date;
    }

    public Message(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
