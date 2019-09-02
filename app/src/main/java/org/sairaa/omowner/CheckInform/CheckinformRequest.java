package org.sairaa.omowner.CheckInform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class CheckinformRequest {

    @SerializedName("hotel_id")
    @Expose
    private int hotel_id;

    @SerializedName("booking_id")
    @Expose
    private int booking_id;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("PhonenoNumber")
    @Expose
    private BigInteger PhonenoNumber;

    @SerializedName("addressproof")
    @Expose
    private String addressproof;

    @SerializedName("uploadphoto")
    @Expose
    private String uploadphoto;

    @SerializedName("uploadproof")
    @Expose
    private String uploadproof;

    @SerializedName("comment")
    @Expose
    private String comment;



    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;


    public CheckinformRequest(int hotel_id, int booking_id, String name, BigInteger phonenoNumber, String addressproof, String uploadphoto, String uploadproof, String comment) {
        this.hotel_id = hotel_id;
        this.booking_id = booking_id;
        Name = name;
        PhonenoNumber = phonenoNumber;
        this.addressproof = addressproof;
        this.uploadphoto = uploadphoto;
        this.uploadproof = uploadproof;
        this.comment = comment;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BigInteger getPhonenoNumber() {
        return PhonenoNumber;
    }

    public void setPhonenoNumber(BigInteger phonenoNumber) {
        PhonenoNumber = phonenoNumber;
    }

    public String getAddressproof() {
        return addressproof;
    }

    public void setAddressproof(String addressproof) {
        this.addressproof = addressproof;
    }

    public String getUploadphoto() {
        return uploadphoto;
    }

    public void setUploadphoto(String uploadphoto) {
        this.uploadphoto = uploadphoto;
    }

    public String getUploadproof() {
        return uploadproof;
    }

    public void setUploadproof(String uploadproof) {
        this.uploadproof = uploadproof;
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
