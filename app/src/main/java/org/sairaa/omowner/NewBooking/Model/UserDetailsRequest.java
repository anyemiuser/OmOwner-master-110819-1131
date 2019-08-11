package org.sairaa.omowner.NewBooking.Model;

public class UserDetailsRequest {
    private String user_id;

    public UserDetailsRequest(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
