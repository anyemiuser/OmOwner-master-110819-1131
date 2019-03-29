package com.anyemi.omrooms.Model;

import java.util.List;

public class UpComing {
    private String status;
    private String msg;
    private List<UpComingBooking> UpcommingBooking;

    public UpComing() {
    }

    public UpComing(String status, String msg, List<UpComingBooking> upcommingBooking) {
        this.status = status;
        this.msg = msg;
        UpcommingBooking = upcommingBooking;
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

    public List<UpComingBooking> getUpcommingBooking() {
        return UpcommingBooking;
    }

    public void setUpcommingBooking(List<UpComingBooking> upcommingBooking) {
        UpcommingBooking = upcommingBooking;
    }
}
