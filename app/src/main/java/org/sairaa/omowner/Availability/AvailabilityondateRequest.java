package org.sairaa.omowner.Availability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailabilityondateRequest {
    @SerializedName("hotel_id")
    @Expose
    private int hotel_id;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("end_date")
    @Expose
    private String end_date;


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

  /*  public RaiseIssueRequest(String name, String email, String password, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }*/

   /* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }*/

    public AvailabilityondateRequest(int hotel_id, String start_date, String end_date) {
        this.hotel_id = hotel_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getMobile_number() {
        return hotel_id;
    }


    public void setMobile_number(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

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
