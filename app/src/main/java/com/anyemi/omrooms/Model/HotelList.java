package com.anyemi.omrooms.Model;

import java.util.List;

public class HotelList {

    private String status;
    private String msg;
    private List<Hotels> hotels;

    public HotelList() {
    }

    public HotelList(String status, String msg, List<Hotels> hotels) {
        this.status = status;
        this.msg = msg;
        this.hotels = hotels;
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

    public List<Hotels> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotels> hotels) {
        this.hotels = hotels;
    }
}
