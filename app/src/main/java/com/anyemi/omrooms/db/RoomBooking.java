package com.anyemi.omrooms.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RoomBooking {
    @PrimaryKey(autoGenerate = false)
    private int hotel_id = 0;
    private String room_type;
    private int no_of_room_booked;
    private int no_of_nights_booked;
    private String from_date;
    private String to_date;
    private String status;
    private String booked_by;
    private String no_of_guests;
    private String price_to_be_paid;

    public RoomBooking(int hotel_id, String room_type, int no_of_room_booked, int no_of_nights_booked, String from_date, String to_date, String status, String booked_by, String no_of_guests, String price_to_be_paid) {
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
}
