package com.anyemi.omrooms.payment;

import android.content.Context;
import android.content.SharedPreferences;



public class SharedPreferenceUtil {


    private SharedPreferences preferences;
    private static final String TAG = "SharedPreferenceUtil";

    private SharedPreferenceUtil() {
        // Add a private constructor to hide the implicit public one.
        // no call here
    }

    /**
     * Landlord Side Constant Parameters
     */


    // Set At account creation, LOgin only
    // need to modify

    public static String setLoginType(Context context, String SupplierId) {

        try {
            saveInPreference(context, "LOGIN_TYPE", SupplierId);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return SupplierId;
    }

    public static String getLoginType(Context context) {
        try {
            return getFromPreference(context, "LOGIN_TYPE", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static String setSessionStartTime(Context context, String session_start_time) {

        try {
            saveInPreference(context, "SESSION_TIME_START", session_start_time);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return session_start_time;
    }

    public static String getSessionStartTime(Context context) {
        try {
            return getFromPreference(context, "SESSION_TIME_START", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static String setPrintHeader(Context context, String header) {

        try {
            saveInPreference(context, "PRINT_HEADER", header);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return header;
    }

    public static String getPrintHeader(Context context) {
        try {
            return getFromPreference(context, "PRINT_HEADER", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }


    public static String setCurrentService(Context context, String service_id) {

        try {
            saveInPreference(context, "CURRENT_SERVICE", service_id);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return service_id;
    }

    public static String getCurrentService(Context context) {
        try {
            return getFromPreference(context, "CURRENT_SERVICE", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }








    public static String setUserData(Context context, String header) {

        try {
            saveInPreference(context, "USER_DATA", header);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return header;
    }

    public static String getUserData(Context context) {
        try {
            return getFromPreference(context, "USER_DATA", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }





    public static String setLoginId(Context context, String header) {

        try {
            saveInPreference(context, "USER_NAME", header);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return header;
    }

    public static String getLoginId(Context context) {
        try {
            return getFromPreference(context, "USER_NAME", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }

    public static String setLoginPassword(Context context, String header) {

        try {
            saveInPreference(context, "LOGIN_PASSWORD", header);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return header;
    }

    public static String getLoginPassword(Context context) {
        try {
            return getFromPreference(context, "LOGIN_PASSWORD", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static String setCurrentServiceId(Context context, String header) {

        try {
            saveInPreference(context, "CURRENT_SERVICE_ID", header);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return header;
    }

    public static String getCurrentServiceId(Context context) {
        try {
            return getFromPreference(context, "CURRENT_SERVICE_ID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static void setMPOSID(Context context, String accessToken) {

        try {
            saveInPreference(context, "MPOSID", accessToken);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getMPOSID(Context context) {
        try {
            return getFromPreference(context, "MPOSID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }


    public static void setMPOSUID(Context context, String accessToken) {

        try {
            saveInPreference(context, "MPOSUID", accessToken);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getMPOSUID(Context context) {
        try {
            return getFromPreference(context, "MPOSUID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

    public static void setMPOSPWD(Context context, String accessToken) {

        try {
            saveInPreference(context, "MPOSPWD", accessToken);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getMPOSPWD(Context context) {
        try {
            return getFromPreference(context, "MPOSPWD", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }









    public static String setUserId(Context context, String SupplierId) {

        try {
            saveInPreference(context, "USER_ID", SupplierId);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return SupplierId;
    }

    public static String getUserId(Context context) {
        try {
            return getFromPreference(context, "USER_ID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }



    public static String setM_PHN_NUM(Context context, String SupplierId) {

        try {
            saveInPreference(context, "M_PHN_NUM", SupplierId);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return SupplierId;
    }

    public static String getM_PHN_NUM(Context context) {
        try {
            return getFromPreference(context, "M_PHN_NUM", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static String setUserEmail(Context context, String usertype) {

        try {
            saveInPreference(context, "USER_EMAIL", usertype);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return usertype;
    }

    public static String getUserEmail(Context context) {
        try {
            return getFromPreference(context, "USER_EMAIL", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }










    /**
     * Tenant Side Constant Parameters
     */


    // Used for create profile in case no previous profiles exist with that Tenant
    // Use Edit rental profile fragment and create Rental Activity Only
    public static void setloginSsoid(Context context, String selectToSendMessage) {

        try {
            saveInPreference(context, "SAVE_LOGIN_SSOID", selectToSendMessage);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getloginSsoid(Context context) {
        try {
            return getFromPreference(context, "SAVE_LOGIN_SSOID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }

// Set At account creation, LOgin only


    public static void setCustomerId(Context context, String customerId) {

        try {
            saveInPreference(context, "CUSTOMER_ID", customerId);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getCustomerId(Context context) {
        try {
            return getFromPreference(context, "CUSTOMER_ID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    // Set At Rental Matches List, LOgin only
    // Set At Matches DEtails Login only
    // To check weather listing saved or not


    public static void setSaveListing(Context context, String Save) {

        try {
            saveInPreference(context, "SAVE_LISTING", Save);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getSaveListing(Context context) {
        try {
            return getFromPreference(context, "SAVE_LISTING", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }

    public static void setLrRequestLeadID(Context context, String LeadID) {

        try {
            saveInPreference(context, "LEAD_ID", LeadID);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getLrRequestLeadID(Context context) {
        try {
            return getFromPreference(context, "LEAD_ID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }

    // Set At Rental Matches List, LOgin only
    // Set At Matches DEtails Login only
    // To check weather listing saved or not
    // try remove this ??????

    public static void setGroupId(Context context, String GROUP_ID) {

        try {
            saveInPreference(context, "GROUP_ID", GROUP_ID);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getGroupId(Context context) {
        try {
            return getFromPreference(context, "GROUP_ID", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static void setcheckbok(Context context, String Delete) {

        try {
            saveInPreference(context, "CHECK", Delete);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getsetcheckbok(Context context) {
        try {
            return getFromPreference(context, "CHECK", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    /**
     * Common Constant Parameters
     */


    // Sublet Login Activity
    // Set at Tenant Login && Landlord Login Only

    public static void setUserName(Context context, String userId) {

        try {
            saveInPreference(context, "USER_D", userId);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getUserName(Context context) {
        try {
            return getFromPreference(context, "USER_D", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }


    public static void setUserFirstName(Context context, String userId) {

        try {
            saveInPreference(context, "UserFirstName", userId);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getUserFirstName(Context context) {
        try {
            return getFromPreference(context, "UserFirstName", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "0";
        }
    }





    // Access Token TO maintain Session

    public static void setAccessToken(Context context, String accessToken) {

        try {
            saveInPreference(context, "ACCESS_TOKEN", accessToken);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getAccessToken(Context context) {
        try {
            return getFromPreference(context, "ACCESS_TOKEN", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }


    public static void setBaseUrl(Context context, String BASE_URL) {

        try {
            saveInPreference(context, "BASE_URL", BASE_URL);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getBaseUrl(Context context) {
        try {
            return getFromPreference(context, "BASE_URL", "");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

/*
// upgrade system
 */
    public static void setWalletBalance(Context context, String promocode) {

        try {
            saveInPreference(context, "Wallet_balance", promocode);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getWalletBalance(Context context) {
        try {
            return getFromPreference(context, "Wallet_balance", "0");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }










    //********************************filters**************************************************//

    public static void saveInPreference(Context context, String key, String value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromPreference(Context context, String key, String defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getString(key, defaultValue);
    }

    public static void saveInPreference(Context context, String key, Boolean value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getFromPreference(Context context, String key, Boolean defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getBoolean(key, defaultValue);
    }

    public static void saveInPreference(Context context, String key, long value) {
        SharedPreferences spf = getSharedPreference(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getFromPreference(Context context, String key, long defaultValue) {
        SharedPreferences spf = getSharedPreference(context);
        return spf.getLong(key, defaultValue);
    }

    public static SharedPreferences getSharedPreference(Context context) {

        return context.getSharedPreferences("OM_ROOMS_PAYMENT", Context.MODE_PRIVATE);
    }

    /**
     * Reset the key and remove the value
     *
     * @param key
     * @param context
     */
    public static void resetKey(String key, Context context) {
        try {
            SharedPreferences spf = getSharedPreference(context);
            SharedPreferences.Editor editor = spf.edit();
            if (spf.contains(key)) {
                editor.remove(key);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////*****fav***///


    ////*****fav***///


    /**
     * Clear all the keys and data
     *
     * @param context
     */
    public static void clearAll(Context context) {
        try {
            SharedPreferences spf = getSharedPreference(context);
            SharedPreferences.Editor editor = spf.edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
