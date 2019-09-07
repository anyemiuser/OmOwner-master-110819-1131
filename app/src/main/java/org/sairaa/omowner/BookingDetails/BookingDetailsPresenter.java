package org.sairaa.omowner.BookingDetails;

import android.util.Log;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Model.CheckOutRequest;
import org.sairaa.omowner.Model.CheckOutResponse;
import org.sairaa.omowner.Model.CustomerBookingDetailsRequest;
import org.sairaa.omowner.Model.CustomerBookingDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsPresenter implements BookingDetailContract.UserActionsListener {

    private static final String TAG_BOOKING_PRESENTER = BookingDetailsPresenter.class.getSimpleName();
    private BookingDetailContract.View bookingView;

    public BookingDetailsPresenter(BookingDetailContract.View bookingView) {
        this.bookingView = bookingView;
    }


    @Override
    public void getDetailsFromResultIntent() {

    }

    @Override
    public void retrieveBookingDetails(CustomerBookingDetailsRequest request) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        bookingView.displayProgressBar("Loading..");
        bookingView.clearRecyclerView();
        omRoomApi.getCustomerBookingDetails(request).enqueue(new Callback<CustomerBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<CustomerBookingDetailsResponse> call, Response<CustomerBookingDetailsResponse> response) {
                if(response.isSuccessful()){

                    CustomerBookingDetailsResponse bookingResponse = response.body();
                    if (bookingResponse != null
                            && bookingResponse.getStatus().equals("success")
                            && bookingResponse.getMessage().equals("success")
                            && bookingResponse.getListofbookings() != null) {
                        bookingView.setUpTitle(request.getStatus());
                        bookingView.hideProgressBar(null);
                        bookingView.setUpRV(bookingResponse.getListofbookings());
                    }else {
                        bookingView.hideProgressBar(" No Data Found.");
                    }

                    Log.e(TAG_BOOKING_PRESENTER,"Success e :"+new Gson().toJson(bookingResponse));
                }else {
                    bookingView.hideProgressBar("Something Went Wrong..");
                    Log.e(TAG_BOOKING_PRESENTER,"Sucess Null: "+response.code()+response.message());
                }
            }

            @Override
            public void onFailure(Call<CustomerBookingDetailsResponse> call, Throwable t) {
                bookingView.hideProgressBar("Something Went Wrong..");

                Log.e(TAG_BOOKING_PRESENTER,"error :"+t.toString());
            }
        });
    }

    @Override
    public void onCheckOut(String bookingId, String cancelledBy) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        CheckOutRequest checkOutRequest = new CheckOutRequest(bookingId,cancelledBy);
        Log.e(TAG_BOOKING_PRESENTER,"check out"+new Gson().toJson(checkOutRequest));
        omRoomApi.doCheckOut(checkOutRequest).enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                if(response.isSuccessful()){
                    CheckOutResponse checkOutResponse = response.body();
                    if (checkOutResponse != null && checkOutResponse.getStatus().equals("Success")) {
                        if(checkOutResponse.getMsg().equals("Successfully  Updated")){
                            bookingView.refreshViewAfterCheckOut(false);
                            bookingView.toastMessage(checkOutResponse.getMsg());
                        }else {
                            bookingView.toastMessage(checkOutResponse.getMsg());
                        }
                    }else{
                        bookingView.toastMessage("Please Make Payment Before Checkout");
                    }
                    Log.e(TAG_BOOKING_PRESENTER,""+new Gson().toJson(checkOutResponse));

                }
            }

            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                Log.e(TAG_BOOKING_PRESENTER,""+t.toString());
            }
        });
    }
}
