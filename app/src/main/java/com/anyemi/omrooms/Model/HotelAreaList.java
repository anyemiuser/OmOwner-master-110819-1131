package com.anyemi.omrooms.Model;

import java.util.List;

public class HotelAreaList {
    private String status;
    private String msg;
    private List<HotelArea> hotels;

    public HotelAreaList() {
    }

    public HotelAreaList(String status, String msg, List<HotelArea> hotels) {
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

    public List<HotelArea> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelArea> hotels) {
        this.hotels = hotels;
    }
}
