package org.sairaa.omowner.Model;

import java.util.List;

public class UserTypeResponse {


    /**
     * status : success
     * message : success
     * usertype : b
     * hoteldetails : [{"hotelid":"902","hotelname":"Vamsi Krishna Residency"}]
     */

    private String status;
    private String message;
    private String usertype;
    private List<HoteldetailsBean> hoteldetails;

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

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public List<HoteldetailsBean> getHoteldetails() {
        return hoteldetails;
    }

    public void setHoteldetails(List<HoteldetailsBean> hoteldetails) {
        this.hoteldetails = hoteldetails;
    }

    public static class HoteldetailsBean {
        /**
         * hotelid : 902
         * hotelname : Vamsi Krishna Residency
         */

        private String hotelid;
        private String hotelname;

        public String getHotelid() {
            return hotelid;
        }

        public void setHotelid(String hotelid) {
            this.hotelid = hotelid;
        }

        public String getHotelname() {
            return hotelname;
        }

        public void setHotelname(String hotelname) {
            this.hotelname = hotelname;
        }
    }
}
