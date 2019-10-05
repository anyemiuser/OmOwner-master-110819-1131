package org.sairaa.omowner.Availability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailabilityondateResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("No_of_classicrooms")
    @Expose
    private String No_of_classicrooms;
    @SerializedName("No_of_deluxerooms")
    @Expose
    private String No_of_deluxerooms;
    @SerializedName("Total_rooms")
    @Expose
    private String Total_rooms;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNo_of_classicrooms() {
        return No_of_classicrooms;
    }

    public void setNo_of_classicrooms(String no_of_classicrooms) {
        No_of_classicrooms = no_of_classicrooms;
    }

    public String getNo_of_deluxerooms() {
        return No_of_deluxerooms;
    }

    public void setNo_of_deluxerooms(String no_of_deluxerooms) {
        No_of_deluxerooms = no_of_deluxerooms;
    }

    public String getTotal_rooms() {
        return Total_rooms;
    }

    public void setTotal_rooms(String total_rooms) {
        Total_rooms = total_rooms;
    }
}
