package org.sairaa.omowner.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomAvailabilityResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("test")
    private List<Asd> test;
    @SerializedName("roomlist")
    private List<RoomIdAvailability> roomlist;


    public RoomAvailabilityResponse() {
    }

    public RoomAvailabilityResponse(String status, String msg, List<Asd> test) {
        this.status = status;
        this.msg = msg;
        this.test = test;
    }

    public RoomAvailabilityResponse(String status, String msg, List<Asd> test, List<RoomIdAvailability> roomlist) {
        this.status = status;
        this.msg = msg;
        this.test = test;
        this.roomlist = roomlist;
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

    public List<Asd> getTest() {
        return test;
    }

    public void setTest(List<Asd> test) {
        this.test = test;
    }

    public List<RoomIdAvailability> getRoomlist() {
        return roomlist;
    }

    public void setRoomlist(List<RoomIdAvailability> roomlist) {
        this.roomlist = roomlist;
    }
}
