package com.anyemi.omrooms.Model;

import java.util.List;

public class CompletedBooking {
    private String status;
    private String msg;
    private List<UpComingBooking> CompletedBooking;

    public CompletedBooking() {
    }

    public CompletedBooking(String status, String msg, List<UpComingBooking> completedBooking) {
        this.status = status;
        this.msg = msg;
        CompletedBooking = completedBooking;
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

    public List<UpComingBooking> getCompletedBooking() {
        return CompletedBooking;
    }

    public void setCompletedBooking(List<UpComingBooking> completedBooking) {
        CompletedBooking = completedBooking;
    }
}
