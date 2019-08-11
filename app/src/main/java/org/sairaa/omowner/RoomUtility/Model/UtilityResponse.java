package org.sairaa.omowner.RoomUtility.Model;

import java.util.List;

public class UtilityResponse {
    private String status;
    private String msg;
    private List<UtilitiyDetails> getUtilityDetails;

    public UtilityResponse() {
    }

    public UtilityResponse(String status, String msg, List<UtilitiyDetails> getUtilityDetails) {
        this.status = status;
        this.msg = msg;
        this.getUtilityDetails = getUtilityDetails;
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

    public List<UtilitiyDetails> getGetUtilityDetails() {
        return getUtilityDetails;
    }

    public void setGetUtilityDetails(List<UtilitiyDetails> getUtilityDetails) {
        this.getUtilityDetails = getUtilityDetails;
    }
}
