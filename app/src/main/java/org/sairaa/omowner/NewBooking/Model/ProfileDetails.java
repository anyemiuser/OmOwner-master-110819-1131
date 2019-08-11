package org.sairaa.omowner.NewBooking.Model;

public class ProfileDetails {
    private String user_id;
    private String user_name;
    private String user_email_id;
    private String user_gender;
    private String user_type;
    private String user_image_url;
    private String user_address;
    private String user_signed_on;
    private String user_updated_on;
    private String user_birthday;
    private String others;

    public ProfileDetails() {
    }

    public ProfileDetails(String user_id, String user_name, String user_email_id, String user_gender, String user_type, String user_image_url, String user_address, String user_signed_on, String user_updated_on, String user_birthday, String others) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email_id = user_email_id;
        this.user_gender = user_gender;
        this.user_type = user_type;
        this.user_image_url = user_image_url;
        this.user_address = user_address;
        this.user_signed_on = user_signed_on;
        this.user_updated_on = user_updated_on;
        this.user_birthday = user_birthday;
        this.others = others;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_signed_on() {
        return user_signed_on;
    }

    public void setUser_signed_on(String user_signed_on) {
        this.user_signed_on = user_signed_on;
    }

    public String getUser_updated_on() {
        return user_updated_on;
    }

    public void setUser_updated_on(String user_updated_on) {
        this.user_updated_on = user_updated_on;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
