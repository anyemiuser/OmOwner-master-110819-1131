package com.anyemi.omrooms.Model;

import java.util.List;

public class HotelAndRoomDetail extends Hotels{


    private List<RoomDetails> roomdetails;



    public HotelAndRoomDetail(String hotel_id, String hotel_name, String hotel_latitude, String hotel_longitude, String hotel_state, String hotel_district, String hotel_city, String hotel_area, String hotel_pin, String hotel_owner_id, String hotel_low_range, String hotel_high_range, String hotel_rating, String hotel_no_of_ratings, String hotel_gst_rate, String hotel_service_rate, String hotel_other_rate, String hotel_image_url, List<RoomDetails> roomdetails) {
        super(hotel_id, hotel_name, hotel_latitude, hotel_longitude, hotel_state, hotel_district, hotel_city, hotel_area, hotel_pin, hotel_owner_id, hotel_low_range, hotel_high_range, hotel_rating, hotel_no_of_ratings, hotel_gst_rate, hotel_service_rate, hotel_other_rate, hotel_image_url);
        this.roomdetails = roomdetails;
    }

    public List<RoomDetails> getRoomdetails() {
        return roomdetails;
    }

    public void setRoomdetails(List<RoomDetails> roomdetails) {
        this.roomdetails = roomdetails;
    }
}
