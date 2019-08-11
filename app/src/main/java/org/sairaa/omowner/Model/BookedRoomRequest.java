package org.sairaa.omowner.Model;

public class BookedRoomRequest {
    private String bookinId;

    public BookedRoomRequest() {
    }

    public BookedRoomRequest(String bookinId) {
        this.bookinId = bookinId;
    }

    public String getBookinId() {
        return bookinId;
    }

    public void setBookinId(String bookinId) {
        this.bookinId = bookinId;
    }
}
