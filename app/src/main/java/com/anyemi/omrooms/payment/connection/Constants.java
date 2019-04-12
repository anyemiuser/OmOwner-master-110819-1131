package com.anyemi.omrooms.payment.connection;

/**
 * Created by SuryaTeja Challa on 17-09-2016.
 **/

public class Constants {

    public static final String LOGIN_TYPE_CDMA = "CDMA Taxes/Bills";
    public static final String LOGIN_TYPE_GVMC = "GVMC Taxes/Bills";
    public static final String LOGIN_TYPE_APEPDCL = "APEPDCL Power Bills";
    public static final String LOGIN_TYPE_RECS = "RECS BILLS";
    public static final String LOGIN_TYPE_CUSTOMER = "14";
    public static final String LOGIN_TYPE_COLLECTION_AGENT = "LOGIN_TYPE_COLLECTION_AGENT";
    public static final String PAYMENT_REQUEST_MODEL = "PAYMENT_REQUEST_MODEL";
    public static final String PAYMENTS_DATA = "PAYMENTS_DATA";
    public static final String INTENT_DATA = "DATA";

    public static final String PRINT_HEADER_APEPDCL = "APEPDCL";
    public static final String PRINT_HEADER_CDMA = "CDMA";
    public static final String PRINT_HEADER_GVMC = "GVMC";
    public static final String PRINT_HEADER_RECS = "RECS";
    public static final String SCREEN_NAME = "SCREEN_NAME";
    public static final String PAYMENT_MODES_SCREEN = "PAYMENT_MODES_SCREEN";
    public static final String CASH_POINT_SERVICE_ID = "46";
    public static final String GVMC_SERVICE_ID = "2";
    public static final String VERSION_ID = "0.3";




    public static final String PAYMENT_MODE_AADHAR = "AADHAR";
    public static final String PAYMENT_MODE_BHARATH_QR = "BQ";
    public static final String PAYMENT_MODE_BHIM = "BHIM";
    public static final String PAYMENT_MODE_CASH = "C";
    public static final String PAYMENT_MODE_ANYEMI_WALLET = "AW";
    public static final String PAYMENT_MODE_CREDIT_CARD = "CC";
    public static final String PAYMENT_MODE_CHEQUE = "CQ";
    public static final String PAYMENT_MODE_DEBIT_CARD = "DC";
    public static final String PAYMENT_MODE_JIO = "Jio";
    public static final String PAYMENT_MODE_NET_BANKING = "NB";
    public static final String PAYMENT_MODE_PAYTM_PG = "Paytm";
    public static final String PAYMENT_MODE_PHONE_PE = "PhonePe";
    public static final String PAYMENT_MODE_PAYTM_QR= "PQ";
    public static final String PAYMENT_MODE_PAYTM_SBI_UPI= "UPI";
    public static final String PAYMENT_MODE_PAY_U_MONEY = "PayU";
    public static final String PAYMENT_MODE_FREE_CHARGE = "FC";
    public static final String PAYMENT_MODE_FREE_AIRTEL = "Airtel";
    public static final String PAYMENT_MODE_FREE_M_PESA = "M-Pesa";
    public static final String PAYMENT_MODE_CASH_POINT = "cashpayment";
    public static final String PAYMENT_MODE_HPCL = "hpclpayment";
    public static final String PAYMENT_MODE_ANNA_CANTEEN = "anna_canteen";
    public static final String PAYMENT_REMARKS = "HPCL";
    public static final String PAYMENT_REMARKS_ANNA = "ANNA_CANTEEN";

    public static final String FIN_ID_HPCL="47";
    public static final String FIN_ID_ANNA="5";




    public static final String PROJECT_NUMBER = "35828915253";

    public static final String PHONE_NUMBER = "P_NUMBER";

    public static final String base_url_dev_http = "https://dev.anyemi.com/webservices/anyemi/";
    public static final String production_base_url = "https://app.anyemi.com/anyemi/";

    public static final String dev_base_url_image = "https://dev.anyemi.com/anyemi_live/app/";
    public static final String production_url_image = "https://anyemi.com/app/";

//    public static final String base_url = SharedPreferenceUtil.getAccessToken(null);
//    public static String base_url = "https://api.anyemi.com/recs/";

    public static String image_url=dev_base_url_image;

    // public static String image_url=base_url_dev_http;

//    public static String base_url=base_url_dev_http;
    public static String base_url=production_base_url;
    public static final String GET_SERVICES_LIST =base_url+"getfinancer";
    public static final String LOGIN_URL =base_url+"login";
    public static final String GET_COSUMER_DETAILS =base_url+"getuseraddress";
    public static final String FORGET_PASSWORD =base_url+"forgetPassword";
    public static final String UPI_LIST =base_url+"getuserUPI";
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

 //public static final String POST_GENERATE_HASH  ="http://192.168.0.17:7071/Nexer/rest/NexerUser/PaytmPg";
    public static final String POST_GENERATE_HASH2  ="http://192.168.0.16:7071/Nexer/rest/NexerUser/PaytmSample";
    public static final String GET_QR_DETAILS  ="https://dev.anyemi.com/webservices/hpcl/getqrcode.php?qr=";





//    public static final String GET_BBPS_BILLER_DETAILS  ="https://dev.anyemi.com/webservices/maheshbank/api.php?f=billermdm";
    public static final String GET_BBPS_BILLER_DETAILS  ="https://app.anyemi.com/maheshbank/billermdm";
//    public static final String POST_BBPS_FETCH_DETAILS  ="https://dev.anyemi.com/webservices/maheshbank/api.php?f=fetch";
    public static final String POST_BBPS_FETCH_DETAILS  ="https://app.anyemi.com/maheshbank/fetch";

//    https://app.anyemi.com/maheshbank/billermdm

}
