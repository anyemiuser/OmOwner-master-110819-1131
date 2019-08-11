package org.sairaa.omowner.Model;

import java.util.List;

public class CompletedCountList {

    private String completedbookings;
    private List<RoomTypeCount> roomtype;

    public CompletedCountList() {
    }

    public CompletedCountList(String completedbookings, List<RoomTypeCount> roomtype) {
        this.completedbookings = completedbookings;
        this.roomtype = roomtype;
    }

    public String getCompletedbookings() {
        return completedbookings;
    }

    public void setCompletedbookings(String completedbookings) {
        this.completedbookings = completedbookings;
    }

    public List<RoomTypeCount> getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(List<RoomTypeCount> roomtype) {
        this.roomtype = roomtype;
    }
}
