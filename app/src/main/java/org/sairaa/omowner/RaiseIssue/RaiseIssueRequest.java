package org.sairaa.omowner.RaiseIssue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaiseIssueRequest {
    @SerializedName("hotel_id")
    @Expose
    private int hotel_id;

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String sub_category;
    @SerializedName("sub_sub_category")
    @Expose
    private String sub_sub_category;
    @SerializedName("comment")
    @Expose
    private String comment;


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

    public RaiseIssueRequest(int hotel_id, String category, String sub_category, String sub_sub_category, String comment) {
        this.hotel_id = hotel_id;
        this.category = category;
        this.sub_category = sub_category;
        this.sub_sub_category = sub_sub_category;
        this.comment = comment;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getSub_sub_category() {
        return sub_sub_category;
    }

    public void setSub_sub_category(String sub_sub_category) {
        this.sub_sub_category = sub_sub_category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
