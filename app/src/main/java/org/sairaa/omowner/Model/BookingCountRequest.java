package org.sairaa.omowner.Model;

public class BookingCountRequest {
    private String hotel_id;
    private String date;
    private String day;

    public BookingCountRequest() {
    }

    public BookingCountRequest(String hotel_id, String date, String day) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.day = day;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
