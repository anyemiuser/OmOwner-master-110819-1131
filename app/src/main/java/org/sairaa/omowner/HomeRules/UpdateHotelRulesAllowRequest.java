package org.sairaa.omowner.HomeRules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateHotelRulesAllowRequest {
    @SerializedName("hotel_id")
    @Expose
    private int hotel_id;

    @SerializedName("notripleoccupancy")
    @Expose
    private String notripleoccupancy;
    @SerializedName("noalcohol")
    @Expose
    private String noalcohol;
    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

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

    public UpdateHotelRulesAllowRequest(int hotel_id, String notripleoccupancy, String noalcohol, String type) {
        this.hotel_id = hotel_id;
        this.notripleoccupancy = notripleoccupancy;
        this.noalcohol = noalcohol;
        this.type = type;

    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getNotripleoccupancy() {
        return notripleoccupancy;
    }

    public void setNotripleoccupancy(String notripleoccupancy) {
        this.notripleoccupancy = notripleoccupancy;
    }

    public String getNoalcohol() {
        return noalcohol;
    }

    public void setNoalcohol(String noalcohol) {
        this.noalcohol = noalcohol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
