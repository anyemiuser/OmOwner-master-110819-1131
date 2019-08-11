package org.sairaa.omowner.NewBooking.Model;

public class RoomTypePrice {
    private String room_type;
    private String room_master_image_url;
    private int noOfNights;
    private String base_Price;
    private String discounted_price;
    private String payble_price;
    private int noOfRoomSelected;
    private boolean isRoomAvailable;

    public RoomTypePrice() {
    }

    public RoomTypePrice(String room_type, String room_master_image_url, int noOfNights, String base_Price, String discounted_price, String payble_price, int noOfRoomSelected, boolean isRoomAvailable) {
        this.room_type = room_type;
        this.room_master_image_url = room_master_image_url;
        this.noOfNights = noOfNights;
        this.base_Price = base_Price;
        this.discounted_price = discounted_price;
        this.payble_price = payble_price;
        this.noOfRoomSelected = noOfRoomSelected;
        this.isRoomAvailable = isRoomAvailable;
    }

    public boolean isRoomAvailable() {
        return isRoomAvailable;
    }

    public void setRoomAvailable(boolean roomAvailable) {
        isRoomAvailable = roomAvailable;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_master_image_url() {
        return room_master_image_url;
    }

    public void setRoom_master_image_url(String room_master_image_url) {
        this.room_master_image_url = room_master_image_url;
    }

    public int getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(int noOfNights) {
        this.noOfNights = noOfNights;
    }

    public String getBase_Price() {
        return base_Price;
    }

    public void setBase_Price(String base_Price) {
        this.base_Price = base_Price;
    }

    public String getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }

    public String getPayble_price() {
        return payble_price;
    }

    public void setPayble_price(String payble_price) {
        this.payble_price = payble_price;
    }

    public int getNoOfRoomSelected() {
        return noOfRoomSelected;
    }

    public void setNoOfRoomSelected(int noOfRoomSelected) {
        this.noOfRoomSelected = noOfRoomSelected;
    }
}
