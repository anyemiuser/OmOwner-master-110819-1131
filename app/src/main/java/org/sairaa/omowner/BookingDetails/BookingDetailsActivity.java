package org.sairaa.omowner.BookingDetails;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.CheckIn.CheckInActivity;
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
import org.sairaa.omowner.payment.PaymentModeActivityNew;
import org.sairaa.omowner.payment.PaymentRequestModel;
import org.sairaa.omowner.payment.instamojo.InstamojoActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity implements BookingDetailContract.View,
        BookingDetailsAdapter.BookingAdapterCallback, Constants {

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
    ImageView uploadproof, uploadphoto;

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
        if (status != null && day != null) {

            CustomerBookingDetailsRequest request = new CustomerBookingDetailsRequest(hotelId, status, day, index);
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
        switch (title) {
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

        if (progressT != null) {
            progressBar.setVisibility(View.GONE);
            progressText.setText(progressT);
        } else {
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
        if (status != null && day != null) {

            if (status.equals(upComingType)) {
                // if checked in then refresh the list to show checked in list to get visual confirmation

                //if Booking is cancelled, Refresh with upcoming only
                //else refresh with in house
                if (!isCancel) {
                    status = inHouseType;
                }
            } else if (status.equals(inHouseType)) {
                status = completedType;
            }
            CustomerBookingDetailsRequest request = new CustomerBookingDetailsRequest(hotelId, status, day, index);
            bookingPresenter.retrieveBookingDetails(request);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void getActionAndBookingList(String bookingType, CustomerBookings list) {
        switch (bookingType) {
            case Extend:
//                Toast.makeText(this, ""+bookingType, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BookingActivity.class);
                intent.putExtra("phoneNo", list.getPhone_no());
                startActivity(intent);
                break;

            case CollectAmount:
/*
                List<BookingModel> bookingModels = new ArrayList<>();
                List<RoomTypePrice> roomT = Objects.requireNonNull(bookingViewModel.getRoomTypesAvailable().getValue());

                int roomCount = 0;
                for(RoomTypePrice roomP: roomT){
                    roomCount = roomCount +roomP.getNoOfRoomSelected();
                }
                if(roomCount == noOfRoom){
                    takeUserDetailsAndBookRoom();
                }else {
                    ToastSnackMessage("Select minimum "+noOfRoom+ " Rooms");
                }*/


                // navigate for check in
                if (ConverterUtil.isDateToday(list.getFrom_date())
                        || ConverterUtil.checkCurrentDateIsLessThenSaved(ConverterUtil.parseDateToddMMMyyyy(list.getFrom_date()))) {


                    //  alertDialog.dismiss();

                    // bookRooms(list.FgetPhone_no());

                   // Intent checkInIntent = new Intent(this, InstamojoActivity.class);
                    Intent checkInIntent = new Intent(this, PaymentModeActivityNew.class);
                    checkInIntent.putExtra("bookingD", "" + new Gson().toJson(list));

                    PaymentRequestModel paymentRequestModel = new PaymentRequestModel();

                    paymentRequestModel.setAssessment_id(list.getBooking_id());
                    paymentRequestModel.setExtrafield(list.getUser_name());
                    paymentRequestModel.setMobile_number(list.getPhone_no());
                    paymentRequestModel.setTotal_amount(list.getPrice_to_be_paid());
                    paymentRequestModel.setActualDueAmount(list.getPrice_to_be_paid());
                    //  paymentRequestModel.setActualDueAmount("300");
                    paymentRequestModel.setServiceCharge("0");

                    Log.d("id", list.getBooking_id());
                    Log.d("name", list.getUser_name());
                    Log.d("no", list.getPhone_no());
                    //checkInIntent.putExtra("bookingD", list.getBooking_id());
                    //checkInIntent.putExtra("bookingD", list.getUser_name());
                    //checkInIntent.putExtra("bookingD", list.getPhone_no());
                    checkInIntent.putExtra(org.sairaa.omowner.payment.Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
                    startActivityForResult(checkInIntent, CheckInRequestCode);
                } else {
                    Toast.makeText(this, "Today is not the Check In Date", Toast.LENGTH_LONG).show();
                }

                break;

            case CheckInOut:

                switch (status) {
                    case upComingType:
                        // navigate for check in


                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        // builder.setTitle("Select Your Booking Procedure");
                        builder.setCancelable(false);
                        View mView = getLayoutInflater().inflate(R.layout.activity_check_in_form, null);
                        builder.setView(mView);


                        TextView etbookid = mView.findViewById(R.id.et_bookid);
                        TextView etname = mView.findViewById(R.id.et_name);
                        TextView etnumber = mView.findViewById(R.id.et_number);

                        uploadphoto = mView.findViewById(R.id.photoupload);
                        uploadproof = mView.findViewById(R.id.proofupload);
                        Spinner sp_addressproof = mView.findViewById(R.id.Spinner_address);
                        Button bt_photoupload = mView.findViewById(R.id.bt_photoupload);
                        Button bt_proofbutton = (Button) mView.findViewById(R.id.bt_proofupload);

                        etbookid.setText(list.getBooking_id());
                        etname.setText(list.getUser_name());
                        etnumber.setText(list.getPhone_no());

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(BookingDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item
                                , getResources().getStringArray(R.array.Addressproof)) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };


                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_addressproof.setAdapter(adapter1);

                        bt_photoupload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 6);
                            }
                        });

                        bt_proofbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                startActivityForResult(intent, 5);
                            }
                        });


                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setCancelable(false);
                        mView.findViewById(R.id.checkincancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.setCancelable(true);
                                alertDialog.dismiss();
                            }
                        });
                        mView.findViewById(R.id.checkinsubmit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!etbookid.getText().toString().equals("")) {
                                    if (!etname.getText().toString().equals("")) {
                                        if (!etnumber.getText().toString().equals("")) {

                                            if (etnumber.getText().toString().length() == 10) {
                                                if (!sp_addressproof.getSelectedItem().toString().equals("Select One")) {

                                                    if (uploadproof.getDrawable() != null) {
                                                        if (uploadphoto.getDrawable() != null) {

                                                            alertDialog.setCancelable(true);
                                                            alertDialog.dismiss();
                                                            // Toast.makeText(BookingDetailsActivity.this, "iuvlbs", Toast.LENGTH_SHORT).show();
                               /* Intent checkInIntent = new Intent(BookingDetailsActivity.this, CheckInActivity.class);
                                startActivity(checkInIntent);*/
                                                       /* if (ConverterUtil.isDateToday(list.getFrom_date())
                                                                || ConverterUtil.checkCurrentDateIsLessThenSaved(ConverterUtil.parseDateToddMMMyyyy(list.getFrom_date()))) {
                                                            Intent checkInIntent = new Intent(BookingDetailsActivity.this, CheckInActivity.class);
                                                            checkInIntent.putExtra("bookingD", "" + new Gson().toJson(list));


                                                            //  Intent checkInIntent = new Intent(BookingDetailsActivity.this, CheckInForm.class);
                                  *//*  Log.d("id",list.getBooking_id());
                                    Log.d("name",list.getUser_name());
                                    Log.d("no",list.getPhone_no());
                                    checkInIntent.putExtra("bookingiD", list.getBooking_id());
                                    checkInIntent.putExtra("bookingname", list.getUser_name());
                                    checkInIntent.putExtra("bookingphonenumber", list.getPhone_no());*//*
//startActivity(checkInIntent);
                                                            startActivityForResult(checkInIntent, 22);
                                                        }*/

                                                            if (ConverterUtil.isDateToday(list.getFrom_date())
                                                                    || ConverterUtil.checkCurrentDateIsLessThenSaved(ConverterUtil.parseDateToddMMMyyyy(list.getFrom_date()))) {
                                                                Intent checkInIntent = new Intent(BookingDetailsActivity.this, CheckInActivity.class);
                                                                checkInIntent.putExtra("bookingD", "" + new Gson().toJson(list));
                                                                startActivityForResult(checkInIntent, CheckInRequestCode);
                                                                Toast.makeText(BookingDetailsActivity.this, "Details Submitted Successfully", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(BookingDetailsActivity.this, "Today is not the Check In Date", Toast.LENGTH_LONG).show();
                                                            }


                                                        } else {
                                                            Toast.makeText(BookingDetailsActivity.this, "Please Upload Photo", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(BookingDetailsActivity.this, "Please Upload Proof", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(BookingDetailsActivity.this, "Select Address Proof ", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(BookingDetailsActivity.this, "Phone number must be 10 digits....", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(BookingDetailsActivity.this, "Phone number Can't be Empty", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(BookingDetailsActivity.this, "Name can't be empty ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(BookingDetailsActivity.this, "booking Id can't be empty ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                        break;
                    case inHouseType:
                        // navigate to check out
                        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                        BookedRoomRequest bookedRoomRequest = new BookedRoomRequest(list.getBooking_id());
                        omRoomApi.getBookedRoomDetailOnEachBooking(bookedRoomRequest).enqueue(new Callback<BokedRoomResponse>() {
                            @Override
                            public void onResponse(Call<BokedRoomResponse> call, Response<BokedRoomResponse> response) {
                                if (response.isSuccessful()) {

                                    BokedRoomResponse bookedResponse = response.body();
                                    Log.e(TAG_BOOKING_DETAIL, "" + new Gson().toJson(bookedResponse));
                                    if (bookedResponse != null && bookedResponse.getStatus().equals("Success")) {
                                        initiateCheckOutDialoge(bookedResponse.getGetBookedRoom(), list.getBooking_id());
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<BokedRoomResponse> call, Throwable t) {
                                Log.e(TAG_BOOKING_DETAIL, "" + t.toString());
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

        String bookingDet = "The Booking Id Is: " + booking_id + "\n" + "Rooms To be Freed : ";
        for (BookedRoom room : getBookedRoom) {
            bookingDet = bookingDet.concat(" ").concat(room.getRoom_id());
        }
        roomText = (TextView) view.findViewById(R.id.check_out_msg);
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
                bookingPresenter.onCheckOut(booking_id, sharedPreferenceConfig.readPhoneNo());

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
        CancelRequest cancelRequest = new CancelRequest(booking_id, sharedPreferenceConfig.readPhoneNo(), "Receptionist");
        omRoomApi.cancelBookedHotel(cancelRequest).enqueue(new Callback<CancelResponse>() {
            @Override
            public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("cancel Response", "" + new Gson().toJson(response.body()));

                    CancelResponse cancelResponse = response.body();
                    Log.e("Response", "" + new Gson().toJson(cancelResponse));
                    if (cancelResponse != null && cancelResponse.getStatus().equals(getString(R.string.success))) {
                        if (cancelResponse.getMsg().equals("Successfully  Cancelled")) {
                            Toast.makeText(BookingDetailsActivity.this, R.string.cancelled_success, Toast.LENGTH_SHORT).show();
//                                            alertDialog.dismiss();
                            refreshViewAfterCheckOut(true);
                        }
                    } else {
                        if (cancelResponse != null) {
                            Toast.makeText(BookingDetailsActivity.this, "" + cancelResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
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
       /* if(requestCode == CheckInRequestCode && resultCode == RESULT_OK){
            refreshViewAfterCheckOut(false);




        }*/

        switch (requestCode) {

            case CheckInRequestCode:
                if (resultCode == RESULT_OK) {
                    refreshViewAfterCheckOut(false);
                }
                break;

            case 7:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Toast.makeText(BookingDetailsActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri1 = data.getData();
                        final InputStream imageStream1 = getContentResolver().openInputStream(imageUri1);
                        final Bitmap selectedImage1 = BitmapFactory.decodeStream(imageStream1);

                        uploadphoto.setImageBitmap(selectedImage1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(BookingDetailsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(BookingDetailsActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        uploadproof.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(BookingDetailsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(BookingDetailsActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;
        }


    }
}
