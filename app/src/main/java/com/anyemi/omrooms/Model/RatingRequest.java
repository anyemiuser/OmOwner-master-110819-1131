package com.anyemi.omrooms.Model;

public class RatingRequest {
    private String booking_id;
    private String hotel_id;
    private String rating;

    public RatingRequest() {
    }

    public RatingRequest(String booking_id, String hotel_id, String rating) {
        this.booking_id = booking_id;
        this.hotel_id = hotel_id;
        this.rating = rating;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
