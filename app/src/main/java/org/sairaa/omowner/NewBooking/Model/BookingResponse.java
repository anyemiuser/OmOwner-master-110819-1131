package org.sairaa.omowner.NewBooking.Model;

public class BookingResponse {
    private String status;
    private String msg;
    private int Booking_id;

    public BookingResponse() {
    }

    public BookingResponse(String status, String msg, int booking_id) {
        this.status = status;
        this.msg = msg;
        Booking_id = booking_id;
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

    public int getBooking_id() {
        return Booking_id;
    }

    public void setBooking_id(int booking_id) {
        Booking_id = booking_id;
    }
}
