package org.sairaa.omowner.Model;

import java.util.List;

public class CustomerBookingDetailsResponse {
    private String status;
    private String message;
    private List<CustomerBookings> listofbookings;

    public CustomerBookingDetailsResponse() {
    }

    public CustomerBookingDetailsResponse(String status, String message, List<CustomerBookings> listofbookings) {
        this.status = status;
        this.message = message;
        this.listofbookings = listofbookings;
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

    public List<CustomerBookings> getListofbookings() {
        return listofbookings;
    }

    public void setListofbookings(List<CustomerBookings> listofbookings) {
        this.listofbookings = listofbookings;
    }
}
