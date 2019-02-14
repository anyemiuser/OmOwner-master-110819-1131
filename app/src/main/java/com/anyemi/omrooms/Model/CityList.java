package com.anyemi.omrooms.Model;

import java.util.List;

public class CityList {
    private String status;
    private String msg;
    private List<CityDistrictState> city;

    public CityList() {
    }

    public CityList(String status, String msg, List<CityDistrictState> city) {
        this.status = status;
        this.msg = msg;
        this.city = city;
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

    public List<CityDistrictState> getCity() {
        return city;
    }

    public void setCity(List<CityDistrictState> city) {
        this.city = city;
    }
}
