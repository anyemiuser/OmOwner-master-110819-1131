package org.sairaa.omowner.NewBooking.Model;

public class ProfileUpdateResponse {
    private String userid;
    private String status;
    private String msg;

    public ProfileUpdateResponse() {
    }

    public ProfileUpdateResponse(String userid, String status, String msg) {
        this.userid = userid;
        this.status = status;
        this.msg = msg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
