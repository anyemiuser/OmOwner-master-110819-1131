package com.anyemi.omrooms.Model;

public class CityDistrictState {
    private String state;
    private String district;
    private String city;

    public CityDistrictState() {
    }

    public CityDistrictState(String state, String district, String city) {
        this.state = state;
        this.district = district;
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
