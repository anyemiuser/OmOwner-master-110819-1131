package org.sairaa.omowner.NewBooking.Model;

import java.util.List;

public class UserDetailsResponse {
    private String  status;
    private String msg;
    private List<ProfileDetails> profileDetails;

    public UserDetailsResponse() {
    }

    public UserDetailsResponse(String status, String msg, List<ProfileDetails> profileDetails) {
        this.status = status;
        this.msg = msg;
        this.profileDetails = profileDetails;
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

    public List<ProfileDetails> getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(List<ProfileDetails> profileDetails) {
        this.profileDetails = profileDetails;
    }
}
