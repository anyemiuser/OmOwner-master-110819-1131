package com.anyemi.omrooms.Model;

public class DiscountDetail {
    private String roomType;
    private double disCountPrice;
    private double paybalePrice;
    private double basePrice;

    public DiscountDetail() {
    }

    public DiscountDetail(String roomType) {
        this.roomType = roomType;
    }

    public DiscountDetail(String roomType, double disCountPrice, double paybalePrice, double basePrice) {
        this.roomType = roomType;
        this.disCountPrice = disCountPrice;
        this.paybalePrice = paybalePrice;
        this.basePrice = basePrice;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(double disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public double getPaybalePrice() {
        return paybalePrice;
    }

    public void setPaybalePrice(double paybalePrice) {
        this.paybalePrice = paybalePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
