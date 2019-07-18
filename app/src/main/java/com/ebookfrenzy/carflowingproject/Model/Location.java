package com.ebookfrenzy.carflowingproject.Model;

public class Location {
    private String carPlate;
    private float bearing;
    private float latitude;
    private float longitude;
    private String geocode;
    private String time;

    public Location() {

    }

    public Location(String carPlate, float bearing, float latitude, float longitude, String geocode, String time) {
        this.carPlate = carPlate;
        this.bearing = bearing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geocode = geocode;
        this.time = time;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
