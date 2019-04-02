package com.anyemi.omrooms.api;

import com.anyemi.omrooms.Model.AreaUnderCity;
import com.anyemi.omrooms.Model.Booking;
import com.anyemi.omrooms.Model.BookingRequest;
import com.anyemi.omrooms.Model.BookingResponse;
import com.anyemi.omrooms.Model.CancelRequest;
import com.anyemi.omrooms.Model.CancelResponse;
import com.anyemi.omrooms.Model.CanceledBooking;
import com.anyemi.omrooms.Model.CityList;
import com.anyemi.omrooms.Model.CompletedBooking;
import com.anyemi.omrooms.Model.HotelAreaList;
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.HotelList;
import com.anyemi.omrooms.Model.TopHotels;
import com.anyemi.omrooms.Model.UpComing;
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

//    https://dev.anyemi.com/webservices/omrooms/Customer/api.php?f=UpcommingBooking

    @POST("api.php")
    Call<UpComing> getUsersUpComingBooking(@Query("f") String typeReport,
                                           @Body BookingRequest bookingRequest);

    @POST("api.php")
    Call<CanceledBooking> getUsersCanceledBooking(@Query("f") String typeReport,
                                                  @Body BookingRequest bookingRequest);

    @POST("api.php")
    Call<CompletedBooking> getUsersCompletedBooking(@Query("f") String typeReport,
                                                    @Body BookingRequest bookingRequest);

//    @POST("api.php?f=registermobilenumbercheck")
//    Call<UserResponse> postUserRegister2(@Field(value = "mobile_number",encoded = false) String mobile_number);

//    @GET("lapi.php")
//    Call<ReportDetails> getResponse(@Query("rquest") String typeReport);
//
    @GET("api.php")
    Call<HotelAreaList> getHotelList(@Query("f") String typeReport,
                                        @Query("city") String city,
                                        @Query("string") String string,
                                        @Query("CheckinDate") String checkIn,
                                        @Query("CheckoutDate") String checkOut,
                                        @Query("NumberofRooms") String noOfRooms);

    @GET("api.php")
    Call<HotelDetails> getHotelDetails(@Query("f") String typeReport,
                                       @Query("hotel_id") String hotelId,
                                       @Query("CheckinDate") String checkIn,
                                       @Query("CheckoutDate") String checkOut,
                                       @Query("noofrooms") String noOfRooms);

    @GET("api.php")
    Call<AreaUnderCity> getAreasInCity(@Query("f") String typeReport,
                                       @Query("city") String city);


    @GET("api.php")
    Call<TopHotels> getTop10HotelInCity(@Query("f") String typeReport,
                                        @Query("state") String state,
                                        @Query("district") String district,
                                        @Query("city") String city);

    @GET("api.php")
    Call<HotelList> getHotelListOnIndexAreaWise(@Query("f") String typeReport,
                                        @Query("State") String state,
                                        @Query("District") String district,
                                        @Query("city") String city,
                                        @Query("Area") String area,
                                        @Query("CheckinDate") String checkIn,
                                        @Query("CheckoutDate") String checkOut,
                                        @Query("NumberofRooms") String noORooms,
                                        @Query("index") String index);

    @GET("api.php")
    Call<CityList> getCityList(@Query("f") String typeReport);

    @POST("api.php?f=Hotelbooking")
    Call<BookingResponse> bookRooms(@Body Booking booking);

    @POST("api.php")
    Call<CancelResponse> cancelBookedHotel(@Query("f") String typeReport,
                                                 @Body CancelRequest cancelRequest);




//
//    @GET("lapi.php")
//    Call<UnbilledServices> getUnbilledServices(@Query("rquest") String typeReport,
//                                               @Query("areacode") String areaCode);






}
