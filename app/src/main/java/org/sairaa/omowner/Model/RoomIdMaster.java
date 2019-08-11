package org.sairaa.omowner.Model;

import java.util.List;

public class RoomIdMaster {
    private String roomType;
    private List<RoomIdAvailability> roomAvailabilityList;

    public RoomIdMaster() {
    }

    public RoomIdMaster(String roomType) {
        this.roomType = roomType;
    }

    public RoomIdMaster(String roomType, List<RoomIdAvailability> roomAvailabilityList) {
        this.roomType = roomType;
        this.roomAvailabilityList = roomAvailabilityList;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<RoomIdAvailability> getRoomAvailabilityList() {
        return roomAvailabilityList;
    }

    public void setRoomAvailabilityList(List<RoomIdAvailability> roomAvailabilityList) {
        this.roomAvailabilityList = roomAvailabilityList;
    }
}
