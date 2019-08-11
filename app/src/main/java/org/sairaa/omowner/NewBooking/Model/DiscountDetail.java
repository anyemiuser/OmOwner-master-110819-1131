package org.sairaa.omowner.NewBooking.Model;

public class DiscountDetail {
    private String roomType;
    private int disCountPrice;
    private int paybalePrice;
    private int basePrice;

    public DiscountDetail() {
    }

    public DiscountDetail(String roomType) {
        this.roomType = roomType;
    }

    public DiscountDetail(String roomType, int disCountPrice, int paybalePrice, int basePrice) {
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

    public int getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(int disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public int getPaybalePrice() {
        return paybalePrice;
    }

    public void setPaybalePrice(int paybalePrice) {
        this.paybalePrice = paybalePrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
}
