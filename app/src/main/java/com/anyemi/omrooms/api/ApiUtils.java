package com.anyemi.omrooms.api;

public class ApiUtils {
    public static final String REGISTER_URL="https://dev.anyemi.com/webservices/omrooms/Customer/";

    public static OmRoomApi getOmRoomApi(){
        return RetrofitClient.getClient(REGISTER_URL).create(OmRoomApi.class);
    }

}
