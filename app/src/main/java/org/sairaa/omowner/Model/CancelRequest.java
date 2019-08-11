package org.sairaa.omowner.Model;

public class CancelRequest {
    private String booking_id;
    private String cancelled_by;
    private String cancel_reason;

    public CancelRequest(String booking_id, String cancelled_by) {
        this.booking_id = booking_id;
        this.cancelled_by = cancelled_by;
    }

    public CancelRequest(String booking_id, String cancelled_by, String cancel_reason) {
        this.booking_id = booking_id;
        this.cancelled_by = cancelled_by;
        this.cancel_reason = cancel_reason;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
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

