package org.sairaa.omowner.Model;

public class RoomTypeDetails {
    private String booked;
    private String room_type;
    private String totalguests;

    public RoomTypeDetails() {
    }

    public RoomTypeDetails(String booked, String room_type, String totalguests) {
        this.booked = booked;
        this.room_type = room_type;
        this.totalguests = totalguests;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getTotalguests() {
        return totalguests;
    }

    public void setTotalguests(String totalguests) {
        this.totalguests = totalguests;
    }
}
