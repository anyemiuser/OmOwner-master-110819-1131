package org.sairaa.omowner.Api;


import org.sairaa.omowner.CancelForm.CancelformRequest;
import org.sairaa.omowner.CheckInform.CheckinformRequest;
import org.sairaa.omowner.Model.BokedRoomResponse;
import org.sairaa.omowner.Model.BookedRoomRequest;
import org.sairaa.omowner.Model.BookingCountRequest;
import org.sairaa.omowner.Model.BookingCountResponse;
import org.sairaa.omowner.Model.CancelRequest;
import org.sairaa.omowner.Model.CancelResponse;
import org.sairaa.omowner.Model.CheckOutRequest;
import org.sairaa.omowner.Model.CheckOutResponse;
import org.sairaa.omowner.Model.PaymentResponseModel;
import org.sairaa.omowner.Model.RoomAvailabilityRequest;
import org.sairaa.omowner.Model.RoomAvailabilityResponse;
import org.sairaa.omowner.Model.CustomerBookingDetailsRequest;
import org.sairaa.omowner.Model.CustomerBookingDetailsResponse;
import org.sairaa.omowner.Model.RoomCheckInRequest;
import org.sairaa.omowner.Model.RoomIdCheckInResponse;
import org.sairaa.omowner.Model.UserTypeRequest;
import org.sairaa.omowner.Model.UserTypeResponse;
import org.sairaa.omowner.NewBooking.Model.Booking;
import org.sairaa.omowner.NewBooking.Model.BookingResponse;
import org.sairaa.omowner.NewBooking.Model.CheckUserExistRequest;
import org.sairaa.omowner.NewBooking.Model.HotelDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileUpdateResponse;
import org.sairaa.omowner.NewBooking.Model.UserDetailsRequest;
import org.sairaa.omowner.NewBooking.Model.UserDetailsResponse;
import org.sairaa.omowner.NewBooking.Model.UserExistResponse;
import org.sairaa.omowner.Pricing.Model.PriceRequest;
import org.sairaa.omowner.Pricing.Model.PriceResponse;
import org.sairaa.omowner.Pricing.Model.RoomTypeList;
import org.sairaa.omowner.Pricing.Model.RoomTypeRequest;
import org.sairaa.omowner.Pricing.Model.UpdateRoomPriceRequest;
import org.sairaa.omowner.Pricing.Model.UpdateRoomPriceResponse;
import org.sairaa.omowner.RaiseIssue.RaiseIssueRequest;
import org.sairaa.omowner.RoomUtility.Model.UtilityRequest;
import org.sairaa.omowner.RoomUtility.Model.UtilityResponse;
import org.sairaa.omowner.RoomUtility.Model.UtilityUpdateRequest;
import org.sairaa.omowner.RoomUtility.Model.UtilityUpdateResponse;

import org.sairaa.omowner.payment.instamojo.GetOrderIDRequest;
import org.sairaa.omowner.payment.instamojo.model.InstamojoPaymentModel;
import org.sairaa.omowner.payment.instamojo.model.IstamojoModel;
import org.sairaa.omowner.payment.instamojo.model.PaymentRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OmRoomApi {

        //api.php?f=HotelListAndUserType

    @POST("Owner/api.php?f=HotelListAndUserType")
    Call<UserTypeResponse> getUserTypeAndHotel(@Body UserTypeRequest userRequest);

    //https://dev.anyemi.com/webservices/omrooms/Owner/api.php?f=hotelStatusOnDate

//    @POST("Owner/api.php?f=hotelStatusOnDate")
    @POST("Owner/api.php?f=hotelStatuss")
    Call<BookingCountResponse> getBookingCountDetails(@Body BookingCountRequest bookingCountRequest);

//    @POST("Owner/api.php?f=listofbookings")
    @POST("Owner/api.php?f=listBookings")
    Call<CustomerBookingDetailsResponse> getCustomerBookingDetails(@Body CustomerBookingDetailsRequest bookingDetailsRequest);

    @POST("Owner/api.php?f=Availability")
    Call<RoomAvailabilityResponse> getCheckInDetails(@Body RoomAvailabilityRequest checkInRequest);

    @POST("Owner/api.php?f=doCheckIn")
    Call<RoomIdCheckInResponse> doCheckIn(@Body RoomCheckInRequest checkInRequest);


    @POST("Owner/api.php?f=getBookedRoom")
    Call<BokedRoomResponse> getBookedRoomDetailOnEachBooking(@Body BookedRoomRequest bookedRoomRequest);

    @POST("Owner/api.php?f=doCheckout")
    Call<CheckOutResponse> doCheckOut(@Body CheckOutRequest checkOutRequest);


    @GET("Customer/api.php")
    Call<HotelDetails> getHotelDetails(@Query("f") String typeReport,
                                       @Query("hotel_id") String hotelId,
                                       @Query("CheckinDate") String checkIn,
                                       @Query("CheckoutDate") String checkOut,
                                       @Query("noofrooms") String noOfRooms);

    @POST("Customer/api.php?f=Hotelbooking")
    Call<BookingResponse> bookRooms(@Body Booking booking);

    @POST("Customer/api.php?f=registermobilenumbercheck")
    Call<UserExistResponse> checkExistingUserOrRegister(@Body CheckUserExistRequest userRequest);

    @POST("Customer/api.php?f=profileupdate")
    Call<ProfileUpdateResponse> updateProfile(@Body ProfileDetails profile);

    @POST("Owner/api.php?f=profileDetails")
    Call<UserDetailsResponse> getUserDetails(@Body UserDetailsRequest userDetailsRequest);

    @POST("Owner/api.php?f=getRoomType")
    Call<RoomTypeList> getRoomType(@Body RoomTypeRequest roomTypeRequest);

    @POST("Owner/api.php?f=getRoomPriceWithType")
    Call<PriceResponse> getRoomPriceOnDate(@Body PriceRequest priceRequest);

    @POST("Owner/api.php?f=updateRoomPrice")
    Call<UpdateRoomPriceResponse> updateRoomPriceDetails(@Body UpdateRoomPriceRequest request);

    @POST("Owner/api.php?f=getUtilityDetails")
    Call<UtilityResponse> getUtilityDetails(@Body UtilityRequest request);

    @POST("Owner/api.php?f=updateUtilityDetails")
    Call<UtilityUpdateResponse> updateUtils(@Body UtilityUpdateRequest request);

    @POST("Customer/api.php?f=CancelBookedHotel")
    Call<CancelResponse> cancelBookedHotel(@Body CancelRequest cancelRequest);

  /*@POST("Owner/api.php?f=hotelcontacts")
    Call<OmContacts> omcontact(@Body OmContacts omcontactsRequest);*/


    @GET("Owner/api.php")
    Call<InstamojoPaymentModel> getInstaMojoPaymentModes(@Query("f") String typeReport);

    @POST("Owner/api.php?f=instamojoorderid")
    Call<IstamojoModel> generateOrderId(@Body GetOrderIDRequest getOrderIDRequest);

    @POST("Owner/api.php?f=pay")
    Call<PaymentResponseModel> postPay(@Body PaymentRequestModel PaymentRequestModel);

    @Headers("Content-Type: application/json")
    @POST("Owner/api.php?f=insertraiseissue")
    Call<RaiseIssueRequest> loginUser(@Body RaiseIssueRequest user);

    @Headers("Content-Type: application/json")
    @POST("Owner/api.php?f=insertaddressproof")
    Call<CheckinformRequest> Checkinform(@Body CheckinformRequest user);

    @Headers("Content-Type: application/json")
    @POST("Owner/api.php?f=CancelReason")
    Call<CancelformRequest> canceluser(@Body CancelformRequest user);

    }
