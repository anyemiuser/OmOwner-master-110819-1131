package org.sairaa.omowner.Model;

import com.google.gson.annotations.SerializedName;

public class Asd {
    @SerializedName("hotel_id")
    private String hotel_id;
    @SerializedName("room_type")
    private String room_type;
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("availability")
    private String availability;

    public Asd() {
    }

    public Asd(String hotel_id, String room_type, String room_id, String availability) {
        this.hotel_id = hotel_id;
        this.room_type = room_type;
        this.room_id = room_id;
        this.availability = availability;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
