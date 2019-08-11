package org.sairaa.omowner.RoomUtility.Model;

public class RoomUtility {
    private String facility;
    private String onOff;

    public RoomUtility() {
    }

    public RoomUtility(String facility, String onOff) {
        this.facility = facility;
        this.onOff = onOff;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }
}
