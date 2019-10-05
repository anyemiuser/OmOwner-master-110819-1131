package org.sairaa.omowner.HomeRules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateHotelRulesRequest {
    @SerializedName("hotel_id")
    @Expose
    private int hotel_id;

    @SerializedName("nononveg")
    @Expose
    private String nononveg;
    @SerializedName("nopak")
    @Expose
    private String nopak;
    @SerializedName("nosmoking")
    @Expose
    private String nosmoking;
    @SerializedName("nooreignguest")
    @Expose
    private String nooreignguest;
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

    public UpdateHotelRulesRequest(int hotel_id, String nononveg, String nopak, String nosmoking, String nooreignguest, String type) {
        this.hotel_id = hotel_id;
        this.nononveg = nononveg;
        this.nopak = nopak;
        this.nosmoking = nosmoking;
        this.nooreignguest = nooreignguest;
        this.type = type;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getNononveg() {
        return nononveg;
    }

    public void setNononveg(String nononveg) {
        this.nononveg = nononveg;
    }

    public String getNopak() {
        return nopak;
    }

    public void setNopak(String nopak) {
        this.nopak = nopak;
    }

    public String getNosmoking() {
        return nosmoking;
    }

    public void setNosmoking(String nosmoking) {
        this.nosmoking = nosmoking;
    }

    public String getNooreignguest() {
        return nooreignguest;
    }

    public void setNooreignguest(String nooreignguest) {
        this.nooreignguest = nooreignguest;
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
