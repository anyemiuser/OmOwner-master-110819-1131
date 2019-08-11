package org.sairaa.omowner.Model;

public class RoomIdType {
    private String room_type;
    private String room_id;

    public RoomIdType() {
    }

    public RoomIdType(String room_type, String room_id) {
        this.room_type = room_type;
        this.room_id = room_id;
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
}
