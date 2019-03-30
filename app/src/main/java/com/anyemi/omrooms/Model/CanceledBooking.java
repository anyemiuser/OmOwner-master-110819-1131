package com.anyemi.omrooms.Model;

import java.util.List;

public class CanceledBooking {

    private String status;
    private String msg;
    private List<UpComingBooking> CanellledBooking;

    public CanceledBooking() {
    }

    public CanceledBooking(String status, String msg, List<UpComingBooking> canellledBooking) {
        this.status = status;
        this.msg = msg;
        CanellledBooking = canellledBooking;
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

    public List<UpComingBooking> getCanellledBooking() {
        return CanellledBooking;
    }

    public void setCanellledBooking(List<UpComingBooking> canellledBooking) {
        CanellledBooking = canellledBooking;
    }
}
