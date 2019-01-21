package com.anyemi.omrooms.Models;

public class Location {

    private String locationName;
    private int locationImage;

    public Location(String locationName, int locationImage) {
        this.locationName = locationName;
        this.locationImage = locationImage;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getLocationImage() {
        return locationImage;
    }
}
