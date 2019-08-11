package org.sairaa.omowner.Model;

import java.util.List;

public class UserTypeResponse {
    private String status;
    private String message;
    private String usertype;
    private List<Hotels> hoteldetails;

    public UserTypeResponse() {
    }

    public UserTypeResponse(String status, String message, String usertype, List<Hotels> hoteldetails) {
        this.status = status;
        this.message = message;
        this.usertype = usertype;
        this.hoteldetails = hoteldetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public List<Hotels> getHoteldetails() {
        return hoteldetails;
    }

    public void setHoteldetails(List<Hotels> hoteldetails) {
        this.hoteldetails = hoteldetails;
    }
}
