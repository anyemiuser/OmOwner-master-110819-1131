package com.anyemi.omrooms.Model;

public class BookingRequest {
    String status;
    String user_id;

    public BookingRequest(String status, String user_id) {
        this.status = status;
        this.user_id = user_id;
    }
}
