package org.sairaa.omowner.RoomUtility.Model;

import org.sairaa.omowner.NewBooking.Model.HotelDetails;

import java.util.List;

public class UtilityUpdateRequest {
    private String hotel_id;
    private List<UtilitiyDetails> UtilityDetails;

    public UtilityUpdateRequest() {
    }

    public UtilityUpdateRequest(String hotel_id, List<UtilitiyDetails> utilityDetails) {
        this.hotel_id = hotel_id;
        UtilityDetails = utilityDetails;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public List<UtilitiyDetails> getUtilityDetails() {
        return UtilityDetails;
    }

    public void setUtilityDetails(List<UtilitiyDetails> utilityDetails) {
        UtilityDetails = utilityDetails;
    }
}
