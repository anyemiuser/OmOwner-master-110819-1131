package com.anyemi.omrooms.Model;

import java.util.List;

public class Booking {
    private String user_id;
    List<BookingModel> bookingModels;
    private String transaction_id;
    private String transaction_status;

    public Booking() {
    }

    public Booking(String user_id, List<BookingModel> bookingModels, String transaction_id, String transaction_status) {
        this.user_id = user_id;
        this.bookingModels = bookingModels;
        this.transaction_id = transaction_id;
        this.transaction_status = transaction_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<BookingModel> getBookingModels() {
        return bookingModels;
    }

    public void setBookingModels(List<BookingModel> bookingModels) {
        this.bookingModels = bookingModels;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }
}
