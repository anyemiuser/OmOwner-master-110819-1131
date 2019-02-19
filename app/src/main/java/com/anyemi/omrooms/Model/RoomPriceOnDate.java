package com.anyemi.omrooms.Model;

public class RoomPriceOnDate {
    private String dateX;
    private String price;
    private String discount;
    private String noOfRoomAvOnDate;

    public RoomPriceOnDate() {
    }

    public RoomPriceOnDate(String dateX, String price, String discount, String noOfRoomAvOnDate) {
        this.dateX = dateX;
        this.price = price;
        this.discount = discount;
        this.noOfRoomAvOnDate = noOfRoomAvOnDate;
    }

    public String getDateX() {
        return dateX;
    }

    public void setDateX(String dateX) {
        this.dateX = dateX;
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

    public String getNoOfRoomAvOnDate() {
        return noOfRoomAvOnDate;
    }

    public void setNoOfRoomAvOnDate(String noOfRoomAvOnDate) {
        this.noOfRoomAvOnDate = noOfRoomAvOnDate;
    }
}
