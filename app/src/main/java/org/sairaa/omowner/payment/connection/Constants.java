package org.sairaa.omowner.payment.connection;

/**
 * Created by SuryaTeja Challa on 17-09-2016.
 **/

public class Constants {


    public static final String base_url_dev_http = "https://dev.anyemi.com/webservices/anyemi/";
    public static final String production_base_url = "https://dev.anyemi.com/webservices/omrooms/Owner/api.php?f=";


   // https://dev.anyemi.com/webservices/omrooms/Owner/api.php?f=getfinancer
//    public static String base_url=base_url_dev_http;
    public static String base_url=production_base_url;
    public static final String GET_SERVICES_LIST =base_url+"getfinancer";
    public static final String LOGIN_URL =base_url+"login";
    public static final String GET_COSUMER_DETAILS =base_url+"getuseraddress";
    public static final String FORGET_PASSWORD =base_url+"forgetPassword";
    public static final String UPI_LIST =base_url+"getuserUPI";
    public static final String ONGO_LIST =base_url+"login_test";
    public static final String RESET_PASSWORD =base_url+"changePassword";
    public static final String REQUEST_WALLET_BALANCE =base_url+"walletrefill";
    public static final String ASS_URL =base_url+"getassment_details";
    public static final String GET_COLLECTIONS  =base_url+"collections?id=";
    //public static final String GET_GAS_BOOKINGS  ="https://dev.anyemi.com/webservices/anyemi/getassignagents?agent_id=";
    public static final String GET_GAS_BOOKINGS  =base_url+"getassignagents?agent_id=";
    public static final String GET_GAS_STATES  =base_url+"getstates";
    public static final String GET_GAS_DISTRICT  =base_url+"getdistrict?id=";
    public static final String GET_GAS_DISTRIBUTORS  =base_url+"getdistributors?id=";
    public static final String GET_WALLET_HISTORY  =base_url+"getwalletrefill?id=";

    public static final String GET_CATEGORIES  =base_url+"getCategory=";
    public static final String POST_PAYMENT_DETAILS  =base_url+"payment";
    public static final String POST_VALID_VPA  =base_url+"checkvpa";
    public static final String POST_PAYMENT_REQUEST_TWO  =base_url+"meTranStatusQueryWeb";
    public static final String POST_PAYMENT_REGISTER  =base_url+"register";
    public static final String POST_PAYMENT_ADD_VPA  =base_url+"storeUPI";
    public static final String POST_PAYMENT_REMOVE_VPA  =base_url+"removeUPI";
    public static final String POST_FEEDBACK  =base_url+"feedback";
    public static final String POST_BOOKING  =base_url+"bookingcyl";
    public static final String POST_UPDATE_NUMBER  =base_url+"updatePhoneNumber";
    public static final String POST_UPDATE_AADHAR  =base_url+"updateUpdateAadhar";
    public static final String POST_RESEND_OTP  =base_url+"resendotp";
    public static final String POST_OTP_VERIFY  =base_url+"otpverify";
    public static final String POST_GAS_OTP_VERIFY  =base_url+"otpverification";
    public static final String POST_SUBMIT_GAS_OTP_VERIFY  =base_url+"otpverifygasbook";
    public static final String POST_PAYMENT_SUBMIT  =base_url+"pay";
    public static final String GET_SAMPLE_PAYMENT_SUBMIT  ="https://pguat.paytm.com/oltp/HANDLER_INTERNAL/getTxnStatus?JsonData=";
    public static final String POST_LOCATION  =base_url+"location";
    public static final String GET_BANKS  =base_url+"getbanks";
    public static final String GET_BRANCHES  =base_url+"getbranches";
    public static final String GET_WALLET_BALANCE  =base_url+"getwalletinfo?id=";
    public static final String GET_PENDING_TRANSACTIONS =base_url+"getPendingTransactions?id=";
    public static final String POST_GENERATE_HASH  =base_url+"checksum";
    public static final String GET_QR_DETAILS  ="https://dev.anyemi.com/webservices/hpcl/getqrcode.php?qr=";

}
