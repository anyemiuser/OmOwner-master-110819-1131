package org.sairaa.omowner.Model;

public class CustomerBookingDetailsRequest {
    private String hotel_id;
    private String status;
    private String day;
    private String index;

    public CustomerBookingDetailsRequest(String hotel_id, String status, String day, String index) {
        this.hotel_id = hotel_id;
        this.status = status;
        this.day = day;
        this.index = index;
    }

    public CustomerBookingDetailsRequest(String day, String index) {
        this.day = day;
        this.index = index;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
