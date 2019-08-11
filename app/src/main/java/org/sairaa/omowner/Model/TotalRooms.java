package org.sairaa.omowner.Model;

public class TotalRooms {

    private String no_of_rooms;
    private String room_type;

    public TotalRooms() {

    }

    public TotalRooms(String no_of_rooms, String room_type) {
        this.no_of_rooms = no_of_rooms;
        this.room_type = room_type;
    }

    public String getNo_of_rooms() {
        return no_of_rooms;
    }

    public void setNo_of_rooms(String no_of_rooms) {
        this.no_of_rooms = no_of_rooms;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
}
