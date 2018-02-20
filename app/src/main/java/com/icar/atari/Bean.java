package com.icar.atari;

import android.util.Log;

/**
 * Created by Parteek on 11/28/2017.
 */

public class Bean {
    static int count =0;
    String designation,name,phone,email,state,city,password;

    {
        count++;
        Log.d("objects","+count");
    }

    public Bean() {

    }

    public Bean(String designation, String name, String phone, String email, String state, String city, String password) {
        this.designation = designation;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.city = city;
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
