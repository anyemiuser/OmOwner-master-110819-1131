package org.sairaa.omowner.Model;

public class RoomTarrif {
    private String room_type;
    private String min_price;
    private String avg_price;
    private String max_price;
    private String min_status;
    private String avg_status;
    private String max_status;
    private String discount;

    public RoomTarrif() {

    }

    public RoomTarrif(String room_type, String min_price, String avg_price, String max_price, String min_status, String avg_status, String max_status, String discount) {
        this.room_type = room_type;
        this.min_price = min_price;
        this.avg_price = avg_price;
        this.max_price = max_price;
        this.min_status = min_status;
        this.avg_status = avg_status;
        this.max_status = max_status;
        this.discount = discount;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getMin_status() {
        return min_status;
    }

    public void setMin_status(String min_status) {
        this.min_status = min_status;
    }

    public String getAvg_status() {
        return avg_status;
    }

    public void setAvg_status(String avg_status) {
        this.avg_status = avg_status;
    }

    public String getMax_status() {
        return max_status;
    }

    public void setMax_status(String max_status) {
        this.max_status = max_status;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
