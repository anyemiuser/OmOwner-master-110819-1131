package com.anyemi.omrooms.Model;

import java.util.List;

public class AreaUnderCity {
    private String status;
    private String msg;
    private List<Location> hotels;

    public AreaUnderCity() {
    }

    public AreaUnderCity(String status, String msg, List<Location> hotels) {
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

    public List<Location> getHotels() {
        return hotels;
    }

    public void setHotels(List<Location> hotels) {
        this.hotels = hotels;
    }
}
