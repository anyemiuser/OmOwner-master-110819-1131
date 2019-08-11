package org.sairaa.omowner.Model;

public class RoomAvailabilityRequest {
    private String hotel_id;

    public RoomAvailabilityRequest(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public RoomAvailabilityRequest() {
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
