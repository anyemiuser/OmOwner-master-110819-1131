package com.anyemi.omrooms.Models;

public class SavedHotels {

    private String savedHotelName;
    private int savedHotelImage;

    public SavedHotels(String savedHotelName, int savedHotelImage) {
        this.savedHotelName = savedHotelName;
        this.savedHotelImage = savedHotelImage;
    }

    public String getSavedHotelName() {
        return savedHotelName;
    }

    public int getSavedHotelImage() {
        return savedHotelImage;
    }
}
