package com.anyemi.omrooms.Model;

public class RoomFacility {

    private String facility;
    private String roomType;

    public RoomFacility(String facility, String roomType) {
        this.facility = facility;
        this.roomType = roomType;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
