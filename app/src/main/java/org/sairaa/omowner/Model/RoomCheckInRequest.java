package org.sairaa.omowner.Model;

import java.util.List;

public class RoomCheckInRequest {
    private String bookinId;
    private String hotelId;
    private String updatedBy;
    private List<RoomIdType> roomIdType;

    public RoomCheckInRequest() {
    }

    public RoomCheckInRequest(String bookinId, String hotelId, String updatedBy, List<RoomIdType> roomIdType) {
        this.bookinId = bookinId;
        this.hotelId = hotelId;
        this.updatedBy = updatedBy;
        this.roomIdType = roomIdType;
    }

    public String getBookinId() {
        return bookinId;
    }

    public void setBookinId(String bookinId) {
        this.bookinId = bookinId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<RoomIdType> getRoomIdType() {
        return roomIdType;
    }

    public void setRoomIdType(List<RoomIdType> roomIdType) {
        this.roomIdType = roomIdType;
    }
}
