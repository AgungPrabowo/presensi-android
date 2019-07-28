package com.example.root.presensi.model;

public class    ModelLocation {
    String id, location_name, location_latitude, location_longitude, location_distance;

    public ModelLocation(String id, String location_name, String location_latitude, String location_longitude, String location_distance) {
        this.id = id;
        this.location_name = location_name;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
        this.location_distance = location_distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getLocation_distance() {
        return location_distance;
    }

    public void setLocation_distance(String location_distance) {
        this.location_distance = location_distance;
    }
}
