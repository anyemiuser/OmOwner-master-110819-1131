package com.anyemi.omrooms.Model;

public class BookingModel {
    private String booking_id;
    private int hotel_id;
    private String room_type;
    private int no_of_room_booked;
    private int no_of_nights_booked;
    private String from_date;
    private String to_date;
    private String booking_date;
    private String status;
    private String booked_by;
    private String checked_in_date;
    private String checked_in_time;
    private String checked_out_date;
    private String checked_out_time;
    private String no_of_guests;
    private String cancelled_on;
    private String cancelled_by;
    private String rating;
    private String price_to_be_paid;

    public BookingModel() {
    }

    public BookingModel(String booking_id, int hotel_id, String room_type, int no_of_room_booked, int no_of_nights_booked, String from_date, String to_date, String booking_date, String status, String booked_by, String checked_in_date, String checked_in_time, String checked_out_date, String checked_out_time, String no_of_guests, String cancelled_on, String cancelled_by, String rating, String price_to_be_paid) {
        this.booking_id = booking_id;
        this.hotel_id = hotel_id;
        this.room_type = room_type;
        this.no_of_room_booked = no_of_room_booked;
        this.no_of_nights_booked = no_of_nights_booked;
        this.from_date = from_date;
        this.to_date = to_date;
        this.booking_date = booking_date;
        this.status = status;
        this.booked_by = booked_by;
        this.checked_in_date = checked_in_date;
        this.checked_in_time = checked_in_time;
        this.checked_out_date = checked_out_date;
        this.checked_out_time = checked_out_time;
        this.no_of_guests = no_of_guests;
        this.cancelled_on = cancelled_on;
        this.cancelled_by = cancelled_by;
        this.rating = rating;
        this.price_to_be_paid = price_to_be_paid;
    }

    public BookingModel(int hotel_id, String room_type, int no_of_room_booked, int no_of_nights_booked, String from_date, String to_date, String status, String booked_by, String no_of_guests, String price_to_be_paid) {
        this.hotel_id = hotel_id;
        this.room_type = room_type;
        this.no_of_room_booked = no_of_room_booked;
        this.no_of_nights_booked = no_of_nights_booked;
        this.from_date = from_date;
        this.to_date = to_date;
        this.status = status;
        this.booked_by = booked_by;
        this.no_of_guests = no_of_guests;
        this.price_to_be_paid = price_to_be_paid;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getNo_of_room_booked() {
        return no_of_room_booked;
    }

    public void setNo_of_room_booked(int no_of_room_booked) {
        this.no_of_room_booked = no_of_room_booked;
    }

    public int getNo_of_nights_booked() {
        return no_of_nights_booked;
    }

    public void setNo_of_nights_booked(int no_of_nights_booked) {
        this.no_of_nights_booked = no_of_nights_booked;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getChecked_in_date() {
        return checked_in_date;
    }

    public void setChecked_in_date(String checked_in_date) {
        this.checked_in_date = checked_in_date;
    }

    public String getChecked_in_time() {
        return checked_in_time;
    }

    public void setChecked_in_time(String checked_in_time) {
        this.checked_in_time = checked_in_time;
    }

    public String getChecked_out_date() {
        return checked_out_date;
    }

    public void setChecked_out_date(String checked_out_date) {
        this.checked_out_date = checked_out_date;
    }

    public String getChecked_out_time() {
        return checked_out_time;
    }

    public void setChecked_out_time(String checked_out_time) {
        this.checked_out_time = checked_out_time;
    }

    public String getNo_of_guests() {
        return no_of_guests;
    }

    public void setNo_of_guests(String no_of_guests) {
        this.no_of_guests = no_of_guests;
    }

    public String getCancelled_on() {
        return cancelled_on;
    }

    public void setCancelled_on(String cancelled_on) {
        this.cancelled_on = cancelled_on;
    }

    public String getCancelled_by() {
        return cancelled_by;
    }

    public void setCancelled_by(String cancelled_by) {
        this.cancelled_by = cancelled_by;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice_to_be_paid() {
        return price_to_be_paid;
    }

    public void setPrice_to_be_paid(String price_to_be_paid) {
        this.price_to_be_paid = price_to_be_paid;
    }
}
