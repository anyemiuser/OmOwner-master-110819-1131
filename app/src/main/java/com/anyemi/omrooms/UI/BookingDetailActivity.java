package com.anyemi.omrooms.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;
import com.google.gson.Gson;

public class BookingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        Intent intent = getIntent();
        String status = intent.getStringExtra("bookingStatus");
        String model = intent.getStringExtra("bookingDetails");
        Gson gson = new Gson();
        UpComingBooking booking = gson.fromJson(model, UpComingBooking.class);

        Log.e("test",""+new Gson().toJson(booking));
    }
}
