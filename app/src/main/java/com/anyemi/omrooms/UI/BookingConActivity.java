package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anyemi.omrooms.Model.Booking;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConstantsData;
import com.google.gson.Gson;

public class BookingConActivity extends AppCompatActivity implements View.OnClickListener, ConstantsData {

    private TextView bookinIdV, bookingShort;
    private Button viewBooking;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_con);
        bookinIdV = findViewById(R.id.booking_id);
        bookingShort = findViewById(R.id.booking_short);
        resultIntent = getIntent();
        String bid = getIntent().getStringExtra("bid");
        String booking = getIntent().getStringExtra("bookingD");
        Booking booking1  = new Gson().fromJson(booking, Booking.class);
        String hotelName = getIntent().getStringExtra("hotelName");
        Log.e("Booking Con Activity",""+new Gson().toJson(booking1));
        bookinIdV.setText("Booking Id : "+bid);
        viewBooking = findViewById(R.id.view_booking);
        viewBooking.setOnClickListener(this);
        String toSet = "";
        if(booking1.getTransaction_id()!= null){
            toSet = toSet.concat(booking1.getTransaction_id()).concat("\n\n\n");
        }

        try{
            toSet = toSet.concat("Your Booking from ")
                    .concat(booking1.getBookingModels().get(0).getFrom_date())
                    .concat(" to ")
                    .concat(booking1.getBookingModels().get(0).getTo_date())
                    .concat(" at Hotel ")
                    .concat(hotelName).concat(" has been Confirmed")
                    .concat("\n\n\n")
//                            .concat(booking1.getTransaction_id()).concat("\n\n\n")
                    .concat("Thank You For Booking with Us..");
            bookingShort.setText(toSet);
        }catch (NullPointerException e){
            bookingShort.setText("Thank You For Booking with Us..");
        }





    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.view_booking:
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                break;
        }

    }
}
