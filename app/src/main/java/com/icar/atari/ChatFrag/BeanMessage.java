package com.icar.atari.ChatFrag;

/**
 * Created by Suraj on 25-01-2018.
 */

// In this model class Firebase can perform automatic serialization in DatabaseReference#setValue() and automatic deserialization in DataSnapshot#getValue().

public class BeanMessage {
    String message, auther, city, time;

    public BeanMessage() {  // Firebase Require this empty constructor for automatic data mapping
    }

    public BeanMessage(String message, String auther, String city, String time) {
        this.message = message;
        this.auther = auther;
        this.city = city;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getAuther() {
        return auther;
    }

    public String getCity() {
        return city;
    }

    public String getTime() {
        return time;
    }
}
