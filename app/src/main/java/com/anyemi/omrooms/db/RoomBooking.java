package com.anyemi.omrooms.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RoomBooking {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String hotel_id;
    private String hotel_name;
    private String hotel_area;
    private String hotel_low_range;
    private String hotel_high_range;
    private String hotel_rating;
    private String hotel_image_url;

    public RoomBooking(String hotel_id, String hotel_name, String hotel_area, String hotel_low_range, String hotel_high_range, String hotel_rating, String hotel_image_url) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_area = hotel_area;
        this.hotel_low_range = hotel_low_range;
        this.hotel_high_range = hotel_high_range;
        this.hotel_rating = hotel_rating;
        this.hotel_image_url = hotel_image_url;
    }


    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_area() {
        return hotel_area;
    }

    public void setHotel_area(String hotel_area) {
        this.hotel_area = hotel_area;
    }

    public String getHotel_low_range() {
        return hotel_low_range;
    }

    public void setHotel_low_range(String hotel_low_range) {
        this.hotel_low_range = hotel_low_range;
    }

    public String getHotel_high_range() {
        return hotel_high_range;
    }

    public void setHotel_high_range(String hotel_high_range) {
        this.hotel_high_range = hotel_high_range;
    }

    public String getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(String hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public String getHotel_image_url() {
        return hotel_image_url;
    }

    public void setHotel_image_url(String hotel_image_url) {
        this.hotel_image_url = hotel_image_url;
    }
}
