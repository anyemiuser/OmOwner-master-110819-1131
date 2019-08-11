package org.sairaa.omowner.Model;

public class RoomCheck {
    private String roomType;
    private int noOfRoom;

    public RoomCheck() {
    }

    public RoomCheck(String roomType, int noOfRoom) {
        this.roomType = roomType;
        this.noOfRoom = noOfRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(int noOfRoom) {
        this.noOfRoom = noOfRoom;
    }
}
