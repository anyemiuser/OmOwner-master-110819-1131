package com.anyemi.omrooms.Model;

import java.util.List;

public class RoomDetails {
    private String noofrooms;
    private String room_type;
    private String ac;
    private String non_ac;
    private String tv;
    private String wheelchair;
    private String bar;
    private String laptop_friendly;
    private String banquet_hall;
    private String room_heater;
    private String dinning_area;
    private String mini_fridge;
    private String power_backup;
    private String elevator;
    private String swimming_pool;
    private String pre_book_meal;
    private String parking_facility;
    private String free_wifi;
    private String card_payment;
    private String gym;
    private String hair_dryer;
    private String laundry;
    private String pet_friendly;
    private String cctv_camera;
    private String geyser;
    private String conference_room;
    private String room_master_image_url;
    private String pay_at_hotel;
    private String room_base_price;
    private String room_discount_percentage;
    private String room_occupation;
    private String room_gst_rate;
    private String room_service_rate;
    private String room_other_rate;
    private List<RoomPriceOnDate> room_prices;

    public RoomDetails() {
    }

    public RoomDetails(String noofrooms, String room_type, String ac, String non_ac, String tv, String wheelchair, String bar, String laptop_friendly, String banquet_hall, String room_heater, String dinning_area, String mini_fridge, String power_backup, String elevator, String swimming_pool, String pre_book_meal, String parking_facility, String free_wifi, String card_payment, String gym, String hair_dryer, String laundry, String pet_friendly, String cctv_camera, String geyser, String conference_room, String room_master_image_url, String pay_at_hotel, String room_base_price, String room_discount_percentage, String room_occupation, String room_gst_rate, String room_service_rate, String room_other_rate, List<RoomPriceOnDate> room_prices) {
        this.noofrooms = noofrooms;
        this.room_type = room_type;
        this.ac = ac;
        this.non_ac = non_ac;
        this.tv = tv;
        this.wheelchair = wheelchair;
        this.bar = bar;
        this.laptop_friendly = laptop_friendly;
        this.banquet_hall = banquet_hall;
        this.room_heater = room_heater;
        this.dinning_area = dinning_area;
        this.mini_fridge = mini_fridge;
        this.power_backup = power_backup;
        this.elevator = elevator;
        this.swimming_pool = swimming_pool;
        this.pre_book_meal = pre_book_meal;
        this.parking_facility = parking_facility;
        this.free_wifi = free_wifi;
        this.card_payment = card_payment;
        this.gym = gym;
        this.hair_dryer = hair_dryer;
        this.laundry = laundry;
        this.pet_friendly = pet_friendly;
        this.cctv_camera = cctv_camera;
        this.geyser = geyser;
        this.conference_room = conference_room;
        this.room_master_image_url = room_master_image_url;
        this.pay_at_hotel = pay_at_hotel;
        this.room_base_price = room_base_price;
        this.room_discount_percentage = room_discount_percentage;
        this.room_occupation = room_occupation;
        this.room_gst_rate = room_gst_rate;
        this.room_service_rate = room_service_rate;
        this.room_other_rate = room_other_rate;
        this.room_prices = room_prices;
    }

    public String getNoofrooms() {
        return noofrooms;
    }

    public void setNoofrooms(String noofrooms) {
        this.noofrooms = noofrooms;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getNon_ac() {
        return non_ac;
    }

    public void setNon_ac(String non_ac) {
        this.non_ac = non_ac;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getLaptop_friendly() {
        return laptop_friendly;
    }

    public void setLaptop_friendly(String laptop_friendly) {
        this.laptop_friendly = laptop_friendly;
    }

    public String getBanquet_hall() {
        return banquet_hall;
    }

    public void setBanquet_hall(String banquet_hall) {
        this.banquet_hall = banquet_hall;
    }

    public String getRoom_heater() {
        return room_heater;
    }

    public void setRoom_heater(String room_heater) {
        this.room_heater = room_heater;
    }

    public String getDinning_area() {
        return dinning_area;
    }

    public void setDinning_area(String dinning_area) {
        this.dinning_area = dinning_area;
    }

    public String getMini_fridge() {
        return mini_fridge;
    }

    public void setMini_fridge(String mini_fridge) {
        this.mini_fridge = mini_fridge;
    }

    public String getPower_backup() {
        return power_backup;
    }

    public void setPower_backup(String power_backup) {
        this.power_backup = power_backup;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getSwimming_pool() {
        return swimming_pool;
    }

    public void setSwimming_pool(String swimming_pool) {
        this.swimming_pool = swimming_pool;
    }

    public String getPre_book_meal() {
        return pre_book_meal;
    }

    public void setPre_book_meal(String pre_book_meal) {
        this.pre_book_meal = pre_book_meal;
    }

    public String getParking_facility() {
        return parking_facility;
    }

    public void setParking_facility(String parking_facility) {
        this.parking_facility = parking_facility;
    }

    public String getFree_wifi() {
        return free_wifi;
    }

    public void setFree_wifi(String free_wifi) {
        this.free_wifi = free_wifi;
    }

    public String getCard_payment() {
        return card_payment;
    }

    public void setCard_payment(String card_payment) {
        this.card_payment = card_payment;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getHair_dryer() {
        return hair_dryer;
    }

    public void setHair_dryer(String hair_dryer) {
        this.hair_dryer = hair_dryer;
    }

    public String getLaundry() {
        return laundry;
    }

    public void setLaundry(String laundry) {
        this.laundry = laundry;
    }

    public String getPet_friendly() {
        return pet_friendly;
    }

    public void setPet_friendly(String pet_friendly) {
        this.pet_friendly = pet_friendly;
    }

    public String getCctv_camera() {
        return cctv_camera;
    }

    public void setCctv_camera(String cctv_camera) {
        this.cctv_camera = cctv_camera;
    }

    public String getGeyser() {
        return geyser;
    }

    public void setGeyser(String geyser) {
        this.geyser = geyser;
    }

    public String getConference_room() {
        return conference_room;
    }

    public void setConference_room(String conference_room) {
        this.conference_room = conference_room;
    }

    public String getRoom_master_image_url() {
        return room_master_image_url;
    }

    public void setRoom_master_image_url(String room_master_image_url) {
        this.room_master_image_url = room_master_image_url;
    }

    public String getPay_at_hotel() {
        return pay_at_hotel;
    }

    public void setPay_at_hotel(String pay_at_hotel) {
        this.pay_at_hotel = pay_at_hotel;
    }

    public String getRoom_base_price() {
        return room_base_price;
    }

    public void setRoom_base_price(String room_base_price) {
        this.room_base_price = room_base_price;
    }

    public String getRoom_discount_percentage() {
        return room_discount_percentage;
    }

    public void setRoom_discount_percentage(String room_discount_percentage) {
        this.room_discount_percentage = room_discount_percentage;
    }

    public String getRoom_occupation() {
        return room_occupation;
    }

    public void setRoom_occupation(String room_occupation) {
        this.room_occupation = room_occupation;
    }

    public String getRoom_gst_rate() {
        return room_gst_rate;
    }

    public void setRoom_gst_rate(String room_gst_rate) {
        this.room_gst_rate = room_gst_rate;
    }

    public String getRoom_service_rate() {
        return room_service_rate;
    }

    public void setRoom_service_rate(String room_service_rate) {
        this.room_service_rate = room_service_rate;
    }

    public String getRoom_other_rate() {
        return room_other_rate;
    }

    public void setRoom_other_rate(String room_other_rate) {
        this.room_other_rate = room_other_rate;
    }

    public List<RoomPriceOnDate> getRoom_prices() {
        return room_prices;
    }

    public void setRoom_prices(List<RoomPriceOnDate> room_prices) {
        this.room_prices = room_prices;
    }
}
