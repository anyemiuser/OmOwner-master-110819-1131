package com.anyemi.omrooms.Model;

import java.util.List;

public class TopHotels {
    private String status;
    private String msg;
    private List<Top10Hotel> hotels;

    public TopHotels() {
    }

    public TopHotels(String status, String msg, List<Top10Hotel> hotels) {
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

    public List<Top10Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Top10Hotel> hotels) {
        this.hotels = hotels;
    }
}
