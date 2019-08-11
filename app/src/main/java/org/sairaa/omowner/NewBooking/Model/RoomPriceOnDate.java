package org.sairaa.omowner.NewBooking.Model;

public class RoomPriceOnDate {
    private String date;
    private String price;
    private String discount;
    private int available_rooms;

    public RoomPriceOnDate() {
    }

    public RoomPriceOnDate(String date, String price, String discount, int available_rooms) {
        this.date = date;
        this.price = price;
        this.discount = discount;
        this.available_rooms = available_rooms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getAvailable_rooms() {
        return available_rooms;
    }

    public void setAvailable_rooms(int available_rooms) {
        this.available_rooms = available_rooms;
    }
}
