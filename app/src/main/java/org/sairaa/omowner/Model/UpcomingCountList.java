package org.sairaa.omowner.Model;

import java.util.List;

public class UpcomingCountList {
    private String Upcomingbookings;
    private List<RoomTypeCount> roomtype;

    public UpcomingCountList() {
    }

    public UpcomingCountList(String upcomingbookings, List<RoomTypeCount> roomtype) {
        Upcomingbookings = upcomingbookings;
        this.roomtype = roomtype;
    }

    public String getUpcomingbookings() {
        return Upcomingbookings;
    }

    public void setUpcomingbookings(String upcomingbookings) {
        Upcomingbookings = upcomingbookings;
    }

    public List<RoomTypeCount> getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(List<RoomTypeCount> roomtype) {
        this.roomtype = roomtype;
    }
}
