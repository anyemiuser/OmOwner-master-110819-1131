package com.anyemi.omrooms.Model;

public class Location {

    private String hotel_area;
    private String hotel_image_url;

    public Location() {
    }

    public Location(String hotel_area, String hotel_image_url) {
        this.hotel_area = hotel_area;
        this.hotel_image_url = hotel_image_url;
    }

    public String getHotel_area() {
        return hotel_area;
    }

    public void setHotel_area(String hotel_area) {
        this.hotel_area = hotel_area;
    }

    public String getHotel_image_url() {
        return hotel_image_url;
    }

    public void setHotel_image_url(String hotel_image_url) {
        this.hotel_image_url = hotel_image_url;
    }
}
