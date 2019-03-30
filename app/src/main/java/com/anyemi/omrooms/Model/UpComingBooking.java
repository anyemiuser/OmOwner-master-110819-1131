package com.anyemi.omrooms.Model;

import java.util.List;

public class UpComingBooking {
    private String booking_id;
    private String hotel_id;
    private String hotel_name;
    private String hotel_city;
    private String hotel_state;
    private String no_of_nights_booked;
    private String booking_date;
    private String from_date;
    private String to_date;
    private String no_of_guests;
    private String price_to_be_paid;
    private String status;
    private String payment_status;
    private String cancelled_on;
    private String cancelled_by;
    private String checked_in_date;
    private String checked_in_time;
    private String checked_out_date;
    private String checked_out_time;
    private String rating;
    private List<BookedRoom> room_details;

    public UpComingBooking() {
    }
    //Upcomming Booking Constructor
    public UpComingBooking(String booking_id, String hotel_id, String hotel_name, String hotel_city, String hotel_state, String no_of_nights_booked, String booking_date, String from_date, String to_date, String no_of_guests, String price_to_be_paid, String status, String payment_status, List<BookedRoom> room_details) {
        this.booking_id = booking_id;
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_city = hotel_city;
        this.hotel_state = hotel_state;
        this.no_of_nights_booked = no_of_nights_booked;
        this.booking_date = booking_date;
        this.from_date = from_date;
        this.to_date = to_date;
        this.no_of_guests = no_of_guests;
        this.price_to_be_paid = price_to_be_paid;
        this.status = status;
        this.payment_status = payment_status;
        this.room_details = room_details;
    }


    //Cancelled Booking Constructor
    public UpComingBooking(String booking_id, String hotel_id, String hotel_name, String hotel_city, String hotel_state, String no_of_nights_booked, String booking_date, String from_date, String to_date, String no_of_guests, String price_to_be_paid, String status, String payment_status, String cancelled_on, String cancelled_by, List<BookedRoom> room_details) {
        this.booking_id = booking_id;
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_city = hotel_city;
        this.hotel_state = hotel_state;
        this.no_of_nights_booked = no_of_nights_booked;
        this.booking_date = booking_date;
        this.from_date = from_date;
        this.to_date = to_date;
        this.no_of_guests = no_of_guests;
        this.price_to_be_paid = price_to_be_paid;
        this.status = status;
        this.payment_status = payment_status;
        this.cancelled_on = cancelled_on;
        this.cancelled_by = cancelled_by;
        this.room_details = room_details;
    }

    //Completed Booking
    public UpComingBooking(String booking_id, String hotel_id, String hotel_name, String hotel_city, String hotel_state, String no_of_nights_booked, String booking_date, String from_date, String to_date, String no_of_guests, String price_to_be_paid, String status, String payment_status, String cancelled_on, String cancelled_by, String checked_in_date, String checked_in_time, String checked_out_date, String checked_out_time, String rating, List<BookedRoom> room_details) {
        this.booking_id = booking_id;
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_city = hotel_city;
        this.hotel_state = hotel_state;
        this.no_of_nights_booked = no_of_nights_booked;
        this.booking_date = booking_date;
        this.from_date = from_date;
        this.to_date = to_date;
        this.no_of_guests = no_of_guests;
        this.price_to_be_paid = price_to_be_paid;
        this.status = status;
        this.payment_status = payment_status;
        this.cancelled_on = cancelled_on;
        this.cancelled_by = cancelled_by;
        this.checked_in_date = checked_in_date;
        this.checked_in_time = checked_in_time;
        this.checked_out_date = checked_out_date;
        this.checked_out_time = checked_out_time;
        this.rating = rating;
        this.room_details = room_details;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getHotel_state() {
        return hotel_state;
    }

    public void setHotel_state(String hotel_state) {
        this.hotel_state = hotel_state;
    }

    public String getNo_of_nights_booked() {
        return no_of_nights_booked;
    }

    public void setNo_of_nights_booked(String no_of_nights_booked) {
        this.no_of_nights_booked = no_of_nights_booked;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
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

    public String getNo_of_guests() {
        return no_of_guests;
    }

    public void setNo_of_guests(String no_of_guests) {
        this.no_of_guests = no_of_guests;
    }

    public String getPrice_to_be_paid() {
        return price_to_be_paid;
    }

    public void setPrice_to_be_paid(String price_to_be_paid) {
        this.price_to_be_paid = price_to_be_paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public List<BookedRoom> getRoom_details() {
        return room_details;
    }

    public void setRoom_details(List<BookedRoom> room_details) {
        this.room_details = room_details;
    }
}
