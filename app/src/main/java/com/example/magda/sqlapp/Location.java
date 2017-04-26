package com.example.magda.sqlapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MAGDA on 2017-04-18.
 */

public class Location {

    //private variables
    int id;
    double latitude;
    double longitude;
    String time;
    float speed;

    // Empty constructor
    public Location() {
    }

    // constructor
    public Location(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }


    // constructor
    public Location(double lat, double lon, String t, float s) {
        this.latitude = lat;
        this.longitude = lon;
        this.time = t ;
        this.speed = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
