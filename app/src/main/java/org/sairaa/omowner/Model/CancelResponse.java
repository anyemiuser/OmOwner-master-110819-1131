package org.sairaa.omowner.Model;

public class CancelResponse {
    private String status;
    private String msg;
    private String cancelled_on;

    public CancelResponse() {
    }

    public CancelResponse(String status, String msg, String cancelled_on) {
        this.status = status;
        this.msg = msg;
        this.cancelled_on = cancelled_on;
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

    public String getCancelled_on() {
        return cancelled_on;
    }

    public void setCancelled_on(String cancelled_on) {
        this.cancelled_on = cancelled_on;
    }
}
