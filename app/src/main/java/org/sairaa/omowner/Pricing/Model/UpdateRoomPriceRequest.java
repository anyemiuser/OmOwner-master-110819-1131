package org.sairaa.omowner.Pricing.Model;

import java.util.List;

public class UpdateRoomPriceRequest {
    private String hotel_id;
    private String date;
    private String price_fed_by;
    private List<RoomTypeAndPrice> roomType;

    public UpdateRoomPriceRequest() {
    }

    public UpdateRoomPriceRequest(String hotel_id, String date, String price_fed_by, List<RoomTypeAndPrice> roomType) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.price_fed_by = price_fed_by;
        this.roomType = roomType;
    }

    public String getPrice_fed_by() {
        return price_fed_by;
    }

    public void setPrice_fed_by(String price_fed_by) {
        this.price_fed_by = price_fed_by;
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

    public List<RoomTypeAndPrice> getRoomType() {
        return roomType;
    }

    public void setRoomType(List<RoomTypeAndPrice> roomType) {
        this.roomType = roomType;
    }
}
