package org.sairaa.omowner.Pricing.Model;

public class UpdateRoomPriceResponse {
    private String status;
    private String message;

    public UpdateRoomPriceResponse() {
    }

    public UpdateRoomPriceResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
