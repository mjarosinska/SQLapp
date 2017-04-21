package com.example.magda.sqlapp;

/**
 * Created by MAGDA on 2017-04-18.
 */

public class Location {

    //private variables
    int id;
    double latitude;
    double longitude;

    String time;

    // Empty constructor
    public Location(){}

    // constructor
    public Location(int id, double lat, double lon){
        this.id = id;
        this.latitude = lat;
        this.longitude = lon;
    }
    // constructor
    public Location(double lat, double lon){
        this.latitude = lat;
        this.longitude = lon;
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

}
