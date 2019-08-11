package org.sairaa.omowner.NewBooking.Model;

public class UserExistResponse {
    private String status;
    private String msg;
    private String mobile_number;

    public UserExistResponse() {
    }

    public UserExistResponse(String status, String msg, String mobile_number) {
        this.status = status;
        this.msg = msg;
        this.mobile_number = mobile_number;
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

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
