package com.anyemi.omrooms.Model;

import java.util.List;

public class HotelDetails {
    private String status;
    private String msg;
    private HotelDetail hoteldetails;
    private String hotel_images_url;
    private String room_images_url;

    public HotelDetails() {
    }

    public HotelDetails(String status, String msg, HotelDetail hoteldetails, String hotel_images_url, String room_images_url) {
        this.status = status;
        this.msg = msg;
        this.hoteldetails = hoteldetails;
        this.hotel_images_url = hotel_images_url;
        this.room_images_url = room_images_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HotelDetail getHoteldetails() {
        return hoteldetails;
    }

    public void setHoteldetails(HotelDetail hoteldetails) {
        this.hoteldetails = hoteldetails;
    }

    public String getHotel_images_url() {
        return hotel_images_url;
    }

    public void setHotel_images_url(String hotel_images_url) {
        this.hotel_images_url = hotel_images_url;
    }

    public String getRoom_images_url() {
        return room_images_url;
    }

    public void setRoom_images_url(String room_images_url) {
        this.room_images_url = room_images_url;
    }
}
