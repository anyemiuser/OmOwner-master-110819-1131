package org.sairaa.omowner.Model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class CustomerBookings {
    private String booking_id;
    private String no_of_nights_booked;
    private String user_name;
    private String phone_no;
    private String from_date;
    private String to_date;
    private String payment_status;
    private String price_to_be_paid;
    private String paidamount;
    private String booking_date;
    private String booked_by;
    private String checked_in_date;
    private String checked_in_time;
    private String checked_out_date;
    private String checked_out_time;
    private List<RoomTypeDetails> roomtype;

    public CustomerBookings() {
    }

    public CustomerBookings(  @NonNull String booking_id, @NonNull String no_of_nights_booked, @Nullable String user_name,
                             @NonNull String phone_no, @NonNull String from_date, @NonNull String to_date, @NonNull String payment_status,
                             @NonNull String price_to_be_paid, @NonNull String paidamount, @NonNull String booking_date, @NonNull String booked_by,
                             @Nullable String checked_in_date, @Nullable String checked_in_time, @Nullable String checked_out_date, @Nullable String checked_out_time, @NonNull List<RoomTypeDetails> roomtype) {
        this.booking_id = booking_id;
        this.no_of_nights_booked = no_of_nights_booked;
        this.user_name = user_name;
        this.phone_no = phone_no;
        this.from_date = from_date;
        this.to_date = to_date;
        this.payment_status = payment_status;
        this.price_to_be_paid = price_to_be_paid;
        this.paidamount = paidamount;
        this.booking_date = booking_date;
        this.booked_by = booked_by;
        this.checked_in_date = checked_in_date;
        this.checked_in_time = checked_in_time;
        this.checked_out_date = checked_out_date;
        this.checked_out_time = checked_out_time;
        this.roomtype = roomtype;

    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getNo_of_nights_booked() {
        return no_of_nights_booked;
    }

    public void setNo_of_nights_booked(String no_of_nights_booked) {
        this.no_of_nights_booked = no_of_nights_booked;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
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

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPrice_to_be_paid() {
        return price_to_be_paid;
    }

    public void setPrice_to_be_paid(String price_to_be_paid) {
        this.price_to_be_paid = price_to_be_paid;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
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

    public List<RoomTypeDetails> getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(List<RoomTypeDetails> roomtype) {
        this.roomtype = roomtype;
    }
}
