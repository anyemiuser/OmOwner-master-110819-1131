package com.anyemi.omrooms.Model;

public class HotelArea {
    private String HotelId;
    private String HotelName;
    private String HotelState;
    private String HotelDist;
    private String HotelCity;
    private String Hotelarea;
    private String HotelPin;

    public HotelArea() {
    }

    public HotelArea(String hotelId, String hotelName, String hotelState, String hotelDist, String hotelCity, String hotelarea, String hotelPin) {
        HotelId = hotelId;
        HotelName = hotelName;
        HotelState = hotelState;
        HotelDist = hotelDist;
        HotelCity = hotelCity;
        Hotelarea = hotelarea;
        HotelPin = hotelPin;
    }

    public String getHotelId() {
        return HotelId;
    }

    public void setHotelId(String hotelId) {
        HotelId = hotelId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getHotelState() {
        return HotelState;
    }

    public void setHotelState(String hotelState) {
        HotelState = hotelState;
    }

    public String getHotelDist() {
        return HotelDist;
    }

    public void setHotelDist(String hotelDist) {
        HotelDist = hotelDist;
    }

    public String getHotelCity() {
        return HotelCity;
    }

    public void setHotelCity(String hotelCity) {
        HotelCity = hotelCity;
    }

    public String getHotelarea() {
        return Hotelarea;
    }

    public void setHotelarea(String hotelarea) {
        Hotelarea = hotelarea;
    }

    public String getHotelPin() {
        return HotelPin;
    }

    public void setHotelPin(String hotelPin) {
        HotelPin = hotelPin;
    }
}
