package org.sairaa.omowner.Api;

public class ApiUtils {
    public static final String REGISTER_URL="https://www.omrooms.com/webservices/omrooms/";

    public static OmRoomApi getOmRoomApi(){
        return RetrofitClient.getClient(REGISTER_URL).create(OmRoomApi.class);
    }

}
