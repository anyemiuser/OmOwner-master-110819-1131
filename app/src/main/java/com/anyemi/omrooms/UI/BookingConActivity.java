package com.anyemi.omrooms.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anyemi.omrooms.R;

public class BookingConActivity extends AppCompatActivity {

    private TextView bookinIdV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_con);
        bookinIdV = findViewById(R.id.booking_id);
        String bid = getIntent().getStringExtra("bid");
        bookinIdV.setText("Booking Id : "+bid);
    }
}
