package org.sairaa.omowner.Model;

import java.util.List;

public class InhouseCountList {

    private String incomingbookings;
    private List<RoomTypeCount> roomtype;

    public InhouseCountList() {
    }

    public InhouseCountList(String incomingbookings, List<RoomTypeCount> roomtype) {
        this.incomingbookings = incomingbookings;
        this.roomtype = roomtype;
    }

    public String getIncomingbookings() {
        return incomingbookings;
    }

    public void setIncomingbookings(String incomingbookings) {
        this.incomingbookings = incomingbookings;
    }

    public List<RoomTypeCount> getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(List<RoomTypeCount> roomtype) {
        this.roomtype = roomtype;
    }
}
