package com.anyemi.omrooms.Models;

public class Hotels {

    private String hotelName;
    private int hotelImage;

    public Hotels(String hotelName, int hotelImage) {
        this.hotelName = hotelName;
        this.hotelImage = hotelImage;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getHotelImage() {
        return hotelImage;
    }
}
