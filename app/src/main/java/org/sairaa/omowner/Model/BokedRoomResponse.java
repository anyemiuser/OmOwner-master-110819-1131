package org.sairaa.omowner.Model;

import java.util.List;

public class BokedRoomResponse {
    private String status;
    private String msg;
    private List<BookedRoom> getBookedRoom;

    public BokedRoomResponse() {
    }

    public BokedRoomResponse(String status, String msg, List<BookedRoom> getBookedRoom) {
        this.status = status;
        this.msg = msg;
        this.getBookedRoom = getBookedRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BookedRoom> getGetBookedRoom() {
        return getBookedRoom;
    }

    public void setGetBookedRoom(List<BookedRoom> getBookedRoom) {
        this.getBookedRoom = getBookedRoom;
    }
}
