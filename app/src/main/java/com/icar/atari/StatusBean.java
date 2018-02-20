package com.icar.atari;

/**
 * Created by Parteek on 12/16/2017.
 */

public class StatusBean {
    String name,location,date,time,profilePicture,statusPicture,status;

    public StatusBean() {

    }

    public StatusBean(String name, String location, String date, String statusPicture, String status) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.statusPicture = statusPicture;
        this.status = status;
    }

    public StatusBean(String name, String location, String date, String time, String profilePicture, String statusPicture, String status) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.profilePicture = profilePicture;
        this.statusPicture = statusPicture;
        this.status = status;
    }

    public StatusBean(String name, String location, String date, String time, String profilePicture, String statusPicture) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.profilePicture = profilePicture;
        this.statusPicture = statusPicture;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getStatusPicture() {
        return statusPicture;
    }

    public String getStatus() {
        return status;
    }
}
