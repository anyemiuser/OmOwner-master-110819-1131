package org.sairaa.omowner.Model;

public class CheckOutRequest {
    private String bookinId;
    private String updatedBy;

    public CheckOutRequest() {
    }

    public CheckOutRequest(String bookinId, String updatedBy) {
        this.bookinId = bookinId;
        this.updatedBy = updatedBy;
    }

    public String getBookinId() {
        return bookinId;
    }

    public void setBookinId(String bookinId) {
        this.bookinId = bookinId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
