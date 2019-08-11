package org.sairaa.omowner.HotelContact;

public class ContactListItem {

    public String manager_number;
    public String manager_email;
    public String reception_number;
    public String reception_email;
    /* public String Description3;
   public String imageUrl;*/

    public ContactListItem(String manager_number, String manager_email, String reception_number, String reception_email) {
        this.manager_number = manager_number;
        this.manager_email = manager_email;
        this.reception_number = reception_number;
        this.reception_email = reception_email;
    }

    public String getManager_number() {
        return manager_number;
    }

    public void setManager_number(String manager_number) {
        this.manager_number = manager_number;
    }

    public String getManager_email() {
        return manager_email;
    }

    public void setManager_email(String manager_email) {
        this.manager_email = manager_email;
    }

    public String getReception_number() {
        return reception_number;
    }

    public void setReception_number(String reception_number) {
        this.reception_number = reception_number;
    }

    public String getReception_email() {
        return reception_email;
    }

    public void setReception_email(String reception_email) {
        this.reception_email = reception_email;
    }
}
