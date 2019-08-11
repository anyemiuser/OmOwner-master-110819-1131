package org.sairaa.omowner.NewBooking.Model;

public class CheckUserExistRequest {

    private String mobile_number;

    public CheckUserExistRequest() {
    }

    public CheckUserExistRequest(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
