package org.sairaa.omowner.Model;

public class RoomIdCheckInResponse {
    private String status;
    private String msg;

    public RoomIdCheckInResponse() {
    }

    public RoomIdCheckInResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
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
}
