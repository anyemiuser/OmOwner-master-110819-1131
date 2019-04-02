package com.anyemi.omrooms.Model;

public class CancelRequest {
    private String booking_id;
    private String cancelled_by;

    public CancelRequest(String booking_id, String cancelled_by) {
        this.booking_id = booking_id;
        this.cancelled_by = cancelled_by;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getCancelled_by() {
        return cancelled_by;
    }

    public void setCancelled_by(String cancelled_by) {
        this.cancelled_by = cancelled_by;
    }
}

