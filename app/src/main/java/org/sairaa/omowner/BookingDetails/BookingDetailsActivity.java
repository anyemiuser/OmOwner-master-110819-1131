package org.sairaa.omowner.BookingDetails;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.CheckIn.CheckInActivity;
import org.sairaa.omowner.CheckIn.CheckInForm;
import org.sairaa.omowner.Collection.CollectionActivity;
import org.sairaa.omowner.Model.BokedRoomResponse;
import org.sairaa.omowner.Model.BookedRoom;
import org.sairaa.omowner.Model.BookedRoomRequest;
import org.sairaa.omowner.Model.CancelRequest;
import org.sairaa.omowner.Model.CancelResponse;
import org.sairaa.omowner.Model.CustomerBookingDetailsRequest;
import org.sairaa.omowner.Model.CustomerBookings;
import org.sairaa.omowner.NewBooking.BookingActivity;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.Constants;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;
import org.sairaa.omowner.instamojo.InstamojoActivity;
import org.sairaa.omowner.instamojo.model.PaymentRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity implements BookingDetailContract.View, BookingDetailsAdapter.BookingAdapterCallback, Constants {

    private static final String TAG_BOOKING_DETAIL = BookingDetailsActivity.class.getName();
    BookingDetailsPresenter bookingPresenter;
    ProgressBar progressBar;
    TextView progressText;
    ConstraintLayout progressLayout;
    private RecyclerView rvCustomerBooking;
    private BookingDetailsAdapter adapter;
    List<CustomerBookings> customerBookingsList = new ArrayList<>();
    private String status, day;
    private String index = "1";
    private String hotelId;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        init();
        hotelId = new SharedPreferenceConfig(this).readHotelId();

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent resultIntent = getIntent();
        status = resultIntent.getStringExtra(Status);
        day = resultIntent.getStringExtra(Day);
        index = resultIntent.getStringExtra(Index);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(status!= null && day != null){

            CustomerBookingDetailsRequest request = new CustomerBookingDetailsRequest(hotelId,status,day,index);
            bookingPresenter.retrieveBookingDetails(request);
        }
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progress_text);
        progressLayout = findViewById(R.id.progress_la);

        rvCustomerBooking = findViewById(R.id.customer_details_booking_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCustomerBooking.setLayoutManager(layoutManager);
        adapter = new BookingDetailsAdapter(this);
        rvCustomerBooking.setAdapter(adapter);


        bookingPresenter = new BookingDetailsPresenter(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
    }

    @Override
    public void setUpTitle(String title) {
        switch (title){
            case upComingType:
                Objects.requireNonNull(getSupportActionBar()).setTitle("Up Coming");
                break;
            case inHouseType:
                Objects.requireNonNull(getSupportActionBar()).setTitle("In House");
                break;
            case completedType:
                Objects.requireNonNull(getSupportActionBar()).setTitle("Completed");
        }

    }

    @Override
    public void displayProgressBar(String progressT) {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressText.setText(progressT);
        progressBar.setMax(100);
    }

    @Override
    public void setProgressBar(int progress) {
        progressBar.setProgress(progress);


    }

    @Override
    public void hideProgressBar(String progressT) {

        if(progressT!= null){
            progressBar.setVisibility(View.GONE);
            progressText.setText(progressT);
        }else {
            progressLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void setUpRV(List<CustomerBookings> bookings) {
        customerBookingsList = bookings;
        adapter.updateList(customerBookingsList);
    }

    @Override
    public void clearRecyclerView() {
        customerBookingsList.clear();
        adapter.updateList(customerBookingsList);
    }

    @Override
    public void refreshViewAfterCheckOut(boolean isCancel) {
        if(status!= null && day != null){

            if(status.equals(upComingType)){
                // if checked in then refresh the list to show checked in list to get visual confirmation

                //if Booking is cancelled, Refresh with upcoming only
                //else refresh with in house
                if(!isCancel){
                    status = inHouseType;
                }
            }else if(status.equals(inHouseType)){
                status = completedType;
            }
            CustomerBookingDetailsRequest request = new CustomerBookingDetailsRequest(hotelId,status,day,index);
            bookingPresenter.retrieveBookingDetails(request);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ""+msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void getActionAndBookingList(String bookingType, CustomerBookings list) {
        switch (bookingType){
            case Extend:
//                Toast.makeText(this, ""+bookingType, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BookingActivity.class);
                intent.putExtra("phoneNo",list.getPhone_no());
                startActivity(intent);
                break;

            case CheckInOut:

                switch (status){
                    case upComingType:
                        // navigate for check in
                        if(ConverterUtil.isDateToday(list.getFrom_date())
                            || ConverterUtil.checkCurrentDateIsLessThenSaved(ConverterUtil.parseDateToddMMMyyyy(list.getFrom_date()))){
                            Intent checkInIntent = new Intent(this, InstamojoActivity.class);
                            //checkInIntent.putExtra("bookingD", ""+new Gson().toJson(list));

                            PaymentRequestModel paymentRequestModel= new PaymentRequestModel();

                            paymentRequestModel.setAssessment_id(list.getBooking_id());
                            paymentRequestModel.setExtrafield(list.getUser_name());
                            paymentRequestModel.setMobile_number(list.getPhone_no());
                            paymentRequestModel.setTotal_amount("300");
                            paymentRequestModel.setActualDueAmount("300");
                            paymentRequestModel.setActualDueAmount("300");
                            paymentRequestModel.setServiceCharge("0");

                            Log.d("id",list.getBooking_id());
                            Log.d("name",list.getUser_name());
                            Log.d("no",list.getPhone_no());
                            //checkInIntent.putExtra("bookingD", list.getBooking_id());
                            //checkInIntent.putExtra("bookingD", list.getUser_name());
                            //checkInIntent.putExtra("bookingD", list.getPhone_no());
                            checkInIntent.putExtra(org.sairaa.omowner.instamojo.Constants.PAYMENT_REQUEST_MODEL,new Gson().toJson(paymentRequestModel));
                            startActivityForResult(checkInIntent,CheckInRequestCode);
                        }else {
                            Toast.makeText(this, "Today is not the Check In Date", Toast.LENGTH_LONG).show();
                        }

                        break;
                    case inHouseType:
                        // navigate to check out
                        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                        BookedRoomRequest bookedRoomRequest = new BookedRoomRequest(list.getBooking_id());
                        omRoomApi.getBookedRoomDetailOnEachBooking(bookedRoomRequest).enqueue(new Callback<BokedRoomResponse>() {
                            @Override
                            public void onResponse(Call<BokedRoomResponse> call, Response<BokedRoomResponse> response) {
                                if(response.isSuccessful()){

                                    BokedRoomResponse bookedResponse = response.body();
                                    Log.e(TAG_BOOKING_DETAIL,""+new Gson().toJson(bookedResponse));
                                    if (bookedResponse != null && bookedResponse.getStatus().equals("Success")) {
                                        initiateCheckOutDialoge(bookedResponse.getGetBookedRoom(), list.getBooking_id());
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<BokedRoomResponse> call, Throwable t) {
                                Log.e(TAG_BOOKING_DETAIL,""+t.toString());
//                                initiateCheckOutDialoge("Trial", list.getBooking_id());
                            }
                        });




//                        Intent checkOuttent = new Intent(this, CheckOutActivity.class);
//                        checkOuttent.putExtra("bookingD", ""+new Gson().toJson(list));
//                        startActivityForResult(checkOuttent,CheckOutRequestCode);
                        break;
                }

//                Toast.makeText(this, ""+bookingType, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initiateCheckOutDialoge(List<BookedRoom> getBookedRoom, String booking_id) {
        TextView roomText;
        Button checkOutFinal, cancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Room To Be Freed On Check out");
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.check_out_details, null);
        builder.setView(view);

        String bookingDet = "The Booking Id Is: "+booking_id+"\n"+"Rooms To be Freed : ";
        for(BookedRoom room: getBookedRoom){
            bookingDet = bookingDet.concat(" ").concat(room.getRoom_id());
        }
        roomText = (TextView)view.findViewById(R.id.check_out_msg);
        checkOutFinal = (Button) view.findViewById(R.id.check_out_final);
        cancel = (Button) view.findViewById(R.id.cancel);
        roomText.setText(bookingDet);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        checkOutFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                bookingPresenter.onCheckOut(booking_id,sharedPreferenceConfig.readPhoneNo());

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });




    }

    @Override
    public void setPaymentRequest(double balanceAmount) {

        Intent intent = new Intent(this, CollectionActivity.class);
        startActivity(intent);

//        Toast.makeText(this, ""+balanceAmount, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getBookingTypeCheckInOrOut() {
        return status;
    }

    @Override
    public void cancelBooking(String booking_id) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        CancelRequest cancelRequest = new CancelRequest(booking_id,sharedPreferenceConfig.readPhoneNo(),"Receptionist");
        omRoomApi.cancelBookedHotel(cancelRequest).enqueue(new Callback<CancelResponse>() {
            @Override
            public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                if(response.isSuccessful()){
                    Log.e("cancel Response",""+new Gson().toJson(response.body()));

                    CancelResponse cancelResponse = response.body();
                    Log.e("Response",""+new Gson().toJson(cancelResponse));
                    if (cancelResponse != null && cancelResponse.getStatus().equals(getString(R.string.success))) {
                        if(cancelResponse.getMsg().equals("Successfully  Cancelled")){
                            Toast.makeText(BookingDetailsActivity.this, R.string.cancelled_success, Toast.LENGTH_SHORT).show();
//                                            alertDialog.dismiss();
                            refreshViewAfterCheckOut(true);
                        }
                    }else {
                        if (cancelResponse != null) {
                            Toast.makeText(BookingDetailsActivity.this, ""+cancelResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(BookingDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CancelResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CheckInRequestCode && resultCode == RESULT_OK)  {
            refreshViewAfterCheckOut(false);
        }
    }
}
