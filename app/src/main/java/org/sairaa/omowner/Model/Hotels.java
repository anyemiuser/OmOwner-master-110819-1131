package org.sairaa.omowner.Model;

public class Hotels {
    private String hotel_id;
    private String hotel_name;

    public Hotels() {
    }

    public Hotels(String hotelid, String hotelname) {
        this.hotel_id = hotelid;
        this.hotel_name = hotelname;
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
}
