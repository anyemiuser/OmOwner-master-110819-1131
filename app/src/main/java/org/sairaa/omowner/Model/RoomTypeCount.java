package org.sairaa.omowner.Model;

public class RoomTypeCount {

    private String room_type;
    private String booked;

    public RoomTypeCount() {
    }

    public RoomTypeCount(String room_type, String booked) {
        this.room_type = room_type;
        this.booked = booked;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }
}
