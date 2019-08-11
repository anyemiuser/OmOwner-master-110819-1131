package org.sairaa.omowner.Model;

public class CheckOutResponse {
    private String status;
    private String msg;

    public CheckOutResponse() {
    }

    public CheckOutResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
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
