package org.sairaa.omowner.Pricing.Model;

public class PriceRequest {
    private String hotel_id;
    private String date;

    public PriceRequest() {
    }

    public PriceRequest(String hotel_id, String date) {
        this.hotel_id = hotel_id;
        this.date = date;
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
}
