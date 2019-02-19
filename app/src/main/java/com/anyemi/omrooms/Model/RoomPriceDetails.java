package com.anyemi.omrooms.Model;

import java.util.List;

public class RoomPriceDetails extends RoomDetails {
    List<RoomPriceOnDate> room_prices;

    public RoomPriceDetails(List<RoomPriceOnDate> room_prices) {
        this.room_prices = room_prices;
    }

    public RoomPriceDetails(String noofrooms, String room_type, String ac, String non_ac, String tv, String wheelchair, String bar, String laptop_friendly, String banquet_hall, String room_heater, String dinning_area, String mini_fridge, String power_backup, String elevator, String swimming_pool, String pre_book_meal, String parking_facility, String free_wifi, String card_payment, String gym, String hair_dryer, String laundry, String pet_friendly, String cctv_camera, String geyser, String conference_room, String room_master_image_url, String pay_at_hotel, String room_base_price, String room_discount_percentage, String room_occupation, String room_gst_rate, String room_service_rate, String room_other_rate, List<RoomPriceOnDate> room_prices) {
        super(noofrooms, room_type, ac, non_ac, tv, wheelchair, bar, laptop_friendly, banquet_hall, room_heater, dinning_area, mini_fridge, power_backup, elevator, swimming_pool, pre_book_meal, parking_facility, free_wifi, card_payment, gym, hair_dryer, laundry, pet_friendly, cctv_camera, geyser, conference_room, room_master_image_url, pay_at_hotel, room_base_price, room_discount_percentage, room_occupation, room_gst_rate, room_service_rate, room_other_rate);
        this.room_prices = room_prices;
    }
}
