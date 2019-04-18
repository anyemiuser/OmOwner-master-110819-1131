package com.anyemi.omrooms.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anyemi.omrooms.Model.BookedRoom;
import com.anyemi.omrooms.Model.CancelRequest;
import com.anyemi.omrooms.Model.CancelResponse;
import com.anyemi.omrooms.Model.RatingRequest;
import com.anyemi.omrooms.Model.RatingResponse;
import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG_BDA = BookingDetailActivity.class.getName();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private TextView titleText, bookinId,bookedBy,fromT,noOfNightT,toT,hotelNameT,cityNameT,guest, noOfRoomT,roomTypeT, priceT,cancelledOnT, checkedInT, checkedOutT;
    private ImageView hotelImageV, bookingImage;
    private ConstraintLayout stayedL, canceledL;
    private Button bookCancelRating;
    private RatingBar ratingBar;

    private String status;
    private String bookingId;

    String reasonCancel = "";

    UpComingBooking booking;
    private float rating = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        Intent intent = getIntent();
        status = intent.getStringExtra("bookingStatus");
        String model = intent.getStringExtra("bookingDetails");
        Gson gson = new Gson();
        booking = gson.fromJson(model, UpComingBooking.class);
        bookingId = booking.getBooking_id();

        Log.e("test","hotel"+bookingId+new Gson().toJson(booking));

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        String title = null;
        String subTitle = null;
        try{
            if(status.equals("u")){
                title = "Confirmed Booking";
                subTitle = "Your Booking has beed Confirmed";
                stayedL.setVisibility(View.GONE);
                canceledL.setVisibility(View.GONE);
                bookCancelRating.setText(" Cancel Booking");
                bookingImage.setImageResource(R.drawable.ic_upcomming);
                ratingBar.setVisibility(View.GONE);
            }else if(status.equals("c")){
                title = "Canceled Booking ";
                subTitle = "Your Booking has beed Canceled";
                canceledL.setVisibility(View.VISIBLE);
                if(booking.getCancelled_on() != null){
                    cancelledOnT.setText("Cancelled On: ".concat(booking.getCancelled_on()));
                }

                stayedL.setVisibility(View.GONE);
                bookCancelRating.setText(" Book Again");
                bookingImage.setImageResource(R.drawable.ic_canceled);
                ratingBar.setVisibility(View.GONE);

            }else {
                title = "Already Stayed Booking ";
                subTitle = "Your Stay at Hotel is Completed";
                canceledL.setVisibility(View.GONE);
                stayedL.setVisibility(View.VISIBLE);
                bookingImage.setImageResource(R.drawable.ic_completed);
                bookCancelRating.setText(" Give Rating");
                ratingBar.setVisibility(View.VISIBLE);
                if(booking.getRating() == null){
                    ratingSubmitForBookin();
                }else {
                    float rate = Float.parseFloat(booking.getRating());
                    ratingBar.setRating(rate);
                }

                checkedInT.setText(" Checked In ".concat(booking.getChecked_in_date()).concat(" at ".concat(booking.getChecked_in_time())));
                checkedOutT.setText("Checked Out ".concat(booking.getChecked_out_date()).concat(" at ").concat(booking.getChecked_out_time()));
            }
        }catch (NullPointerException e){
            Log.e(TAG_BDA,""+e.toString());
            Toast.makeText(this, "Proper Information Not Found", Toast.LENGTH_SHORT).show();

        }

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(title);
        }




        titleText.setText(subTitle);
        bookinId.setText("Booking Id: ".concat(booking.getBooking_id()));
//        bookedBy.setText(booking.getB);
        fromT.setText("From ".concat(booking.getFrom_date()).concat("  for "));
        noOfNightT.setText(booking.getNo_of_nights_booked().concat(" Night "));
        toT.setText("Up to ".concat(booking.getTo_date()));
        hotelNameT.setText(booking.getHotel_name());
        cityNameT.setText(booking.getHotel_city());
        guest.setText("Guests: ".concat(booking.getNo_of_guests()));
        List<BookedRoom> roomB = booking.getRoom_details();
        int noOfRoom;
        String roomType = "";
        for(BookedRoom bookRoom: roomB){
            roomType = roomType.concat(bookRoom.getRoom_type().concat(" :").concat(bookRoom.getNoofroomsbooked()).concat(" rooms ").concat("\n"));

        }

//        noOfRoomT.setText("Rooms: ".concat(booking.getRoom_details().size()));,
        roomTypeT.setText(roomType);
        priceT.setText("Total Price: ".concat(booking.getPrice_to_be_paid()));
//        cancelledOnT, checkedInT, checkedOutT;
////        private ImageView hotelImageV;
//        if(status)stayedL, canceledL;








    }

    private void init() {
        titleText = findViewById(R.id.title_text);
        bookinId = findViewById(R.id.booking_id_text);
        ratingBar =  findViewById(R.id.rating_bar_user);
        fromT =  findViewById(R.id.from_text);
        noOfNightT =  findViewById(R.id.no_nighit_text);
        toT =  findViewById(R.id.to_text);
        hotelNameT =  findViewById(R.id.hotel_name_text);
        cityNameT =  findViewById(R.id.city_text);
        guest =  findViewById(R.id.guests_text);
        noOfRoomT =  findViewById(R.id.no_room_text);
        roomTypeT =  findViewById(R.id.room_type_no_text);
        priceT =  findViewById(R.id.price_text);
        cancelledOnT =  findViewById(R.id.cancelled_on_text);
        checkedInT = findViewById(R.id.stayed_on_text);
        checkedOutT =  findViewById(R.id.checked_out_text);

        hotelImageV =  findViewById(R.id.hotel_image_viewT);
        bookingImage = findViewById(R.id.booking_image_tool);

        stayedL =  findViewById(R.id.stayed_layout);
        canceledL =  findViewById(R.id.cancelled_layout);

        bookCancelRating =  findViewById(R.id.book_cancel_t);
        bookCancelRating.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.book_cancel_t:
                decideTypeBooking(status);
                break;
        }
    }

    private void decideTypeBooking(String status) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(this);
        String userId = sharedPreferenceConfig.readPhoneNo();

        switch (status){
            case "u":
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                View view = getLayoutInflater().inflate(R.layout.alert_cancel_booking_reason, null);
                builder.setView(view);
                Spinner spinner = (Spinner)view.findViewById(R.id.spinner_cancel_reson);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        reasonCancel = adapterView.getSelectedItem().toString().trim();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);

                view.findViewById(R.id.yes_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CancelRequest cancelRequest = new CancelRequest(bookingId,userId,reasonCancel);
                        Log.e("Body",""+new Gson().toJson(cancelRequest));
                        omRoomApi.cancelBookedHotel("CancelBookedHotel ",cancelRequest).enqueue(new Callback<CancelResponse>() {
                            @Override
                            public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                                alertDialog.dismiss();
                                if(response.isSuccessful()){
                                    Log.e("cancel Response",""+new Gson().toJson(response.body()));

                                    CancelResponse cancelResponse = response.body();
                                    Log.e("Response",""+new Gson().toJson(cancelResponse));
                                    if (cancelResponse != null && cancelResponse.getStatus().equals(getString(R.string.success))) {
                                        if(cancelResponse.getMsg().equals("Successfully  Cancelled")){
                                            Toast.makeText(BookingDetailActivity.this, R.string.cancelled_success, Toast.LENGTH_SHORT).show();
//                                            alertDialog.dismiss();
                                            finish();
                                        }
                                    }else {
                                        if (cancelResponse != null) {
                                            Toast.makeText(BookingDetailActivity.this, ""+cancelResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else {
                                    Toast.makeText(BookingDetailActivity.this, R.string.went_wrong, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<CancelResponse> call, Throwable t) {
                                Log.e("failed",""+t.toString());

                            }
                        });

                        alertDialog.dismiss();
//                        finish();

                    }
                });

                view.findViewById(R.id.no_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });



//                new AlertDialog.Builder(this)
//                        .setTitle("Cancel Booking")
//                        .setMessage("Are you sure to cancel the Booking")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                dialogInterface.dismiss();
//                                finish();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();

//                Toast.makeText(this, "u", Toast.LENGTH_SHORT).show();
                break;

            case "c":
                Intent intent = new Intent(this,HotelActivity.class);
                intent.putExtra("hotelId",booking.getHotel_id());
                intent.putExtra("hotelName",booking.getHotel_name());
                startActivity(intent);

                break;

            case "s":
                ratingSubmitForBookin();

                break;

        }
    }

    private void ratingSubmitForBookin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thanks For the Stay ! How Was Your Stay ?");
        builder.setCancelable(false);
        View mView = getLayoutInflater().inflate(R.layout.rating_alert_layout, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        RatingBar ratingBar = mView.findViewById(R.id.ratingBar);

        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setRating(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = ratingBar.getRating();
            }
        });
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rating = ratingBar.getRating();
            }
        });

//        mView.findViewById(R.id.submit_rating).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(BookingDetailActivity.this, ""+rating, Toast.LENGTH_SHORT).show();
//                alertDialog.dismiss();
//            }
//        });
        mView.findViewById(R.id.submit_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(true);
                alertDialog.dismiss();
                RatingRequest ratingRequest = new RatingRequest(booking.getBooking_id(),booking.getHotel_id(),String.valueOf(rating));
                OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                omRoomApi.updateRating("rating ",ratingRequest).enqueue(new Callback<RatingResponse>() {
                    @Override
                    public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                        if(response.isSuccessful()){
                            RatingResponse ratingResponse = response.body();
                            Log.e("rating",""+new Gson().toJson(response.body()));
                            if (ratingResponse != null && ratingResponse.getStatus().equals("Success") &&
                                    ratingResponse.getMsg().equals("Successfully  Updated")) {

                                Log.e("rating success",""+new Gson().toJson(ratingResponse));
                                ratingBar.setRating(rating);
                                bookCancelRating.setVisibility(View.GONE);
                                Toast.makeText(BookingDetailActivity.this, "Rating Updated: "+rating, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RatingResponse> call, Throwable t) {

                        Log.e("rating failed",""+t.toString());


                    }
                });
//                        booking.setTransaction_id(null);
//                        booking.setTransaction_status(null);
//                        Log.e(TAG_HOTEL,"pay at hotel: "+new Gson().toJson(booking));
//                        bookRooms(booking);

            }
        });
    }
}
