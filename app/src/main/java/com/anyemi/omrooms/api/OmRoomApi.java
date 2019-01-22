package com.anyemi.omrooms.api;

import com.anyemi.omrooms.Model.UserRequest;
import com.anyemi.omrooms.Model.UserResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OmRoomApi {
//    https://newsapi.org/v2/top-headlines?country=in&apiKey=c19366b11c0440848041a33b1745e3d1
    //api.php?f=registermobilenumbercheck
    @POST("api.php?f=registermobilenumbercheck")
    Call<UserResponse> postUserRegister(@Body UserRequest userRequest);

//    @POST("api.php?f=registermobilenumbercheck")
//    Call<UserResponse> postUserRegister2(@Field(value = "mobile_number",encoded = false) String mobile_number);

//    @GET("lapi.php")
//    Call<ReportDetails> getResponse(@Query("rquest") String typeReport);
//
//    @GET("lapi.php")
//    Call<CategoryDetails> getTotalService(@Query("rquest") String typeReport);
//
//    @GET("lapi.php")
//    Call<UnbilledServices> getUnbilledServices(@Query("rquest") String typeReport,
//                                               @Query("areacode") String areaCode);






}