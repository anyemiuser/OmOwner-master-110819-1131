package org.sairaa.omowner.Model;

import java.util.List;

public class CancelledCountList {

    private String cancelledbookings;
    private List<RoomTypeCount> roomtype;

    public CancelledCountList() {
    }

    public CancelledCountList(String cancelledbookings, List<RoomTypeCount> roomtype) {
        this.cancelledbookings = cancelledbookings;
        this.roomtype = roomtype;
    }

    public String getCancelledbookings() {
        return cancelledbookings;
    }

    public void setCancelledbookings(String cancelledbookings) {
        this.cancelledbookings = cancelledbookings;
    }

    public List<RoomTypeCount> getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(List<RoomTypeCount> roomtype) {
        this.roomtype = roomtype;
    }
}
