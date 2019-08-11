package org.sairaa.omowner.Model;

public class UserTypeRequest {
    private String user_id;

    public UserTypeRequest(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
