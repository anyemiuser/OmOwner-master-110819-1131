package org.sairaa.omowner.RoomUtility.Model;

public class UtilityRequest {
    private String hotel_id;

    public UtilityRequest() {
    }

    public UtilityRequest(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
