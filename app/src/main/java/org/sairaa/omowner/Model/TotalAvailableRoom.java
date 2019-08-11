package org.sairaa.omowner.Model;

public class TotalAvailableRoom extends TotalRooms {
    private int roomBooked;

    public TotalAvailableRoom(int roomBooked) {
        this.roomBooked = roomBooked;
    }



    public TotalAvailableRoom(String no_of_rooms, String room_type, int roomBooked) {
        super(no_of_rooms, room_type);
        this.roomBooked = roomBooked;
    }
}
