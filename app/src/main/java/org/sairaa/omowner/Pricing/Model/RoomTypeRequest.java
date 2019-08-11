package org.sairaa.omowner.Pricing.Model;

public class RoomTypeRequest {
    private String hotel_id;

    public RoomTypeRequest() {
    }

    public RoomTypeRequest(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
