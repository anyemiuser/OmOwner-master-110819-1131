package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.anyemi.omrooms.Fragments.CalenderFragmentCheckIn;
import com.anyemi.omrooms.Fragments.CalenderFragmentCheckOut;
import com.anyemi.omrooms.Fragments.FragmentAdapter.CalenderFragmentAdapter;
import com.anyemi.omrooms.Fragments.RoomGuestFragment;
import com.anyemi.omrooms.R;

public class CalenderActivity extends AppCompatActivity implements RoomGuestFragment.OnFragmentInteractionListener,
        CalenderFragmentCheckIn.OnFragmentInteractionListenerC,
        CalenderFragmentCheckOut.OnFragmentInteractionListenerC,
        CalenderFragmentCheckIn.OnFragmentChangeListner,
        CalenderFragmentCheckOut.OnFragmentChangeListner,
        View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static String checkIn;
    public static String checkOut;
    public static String rooms;
    public static String guests;

    private Button applyChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        applyChanges = findViewById(R.id.apply);
        applyChanges.setOnClickListener(this);

        int fragmentId = getIntent().getIntExtra("check",-1);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setTitle(R.string.checkin);
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        //fragment inside fragment
        CalenderFragmentAdapter adapter = new CalenderFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CalenderFragmentCheckIn(),"Check In");
        adapter.addFragment(new CalenderFragmentCheckOut(),"Check Out");
        adapter.addFragment(new RoomGuestFragment(),"Room & Guest");
        viewPager.setAdapter(adapter);
        if(fragmentId != -1){
            switch (fragmentId){
                case 0:
                    actionbar.setTitle("Select Check-In Date");
                    break;
                case 1:
                    actionbar.setTitle("Select Check-Out Date");
                    break;
                case 2:
                    actionbar.setTitle("Select Room & Guest");
                    break;
                default:
                    actionbar.setTitle("Select Check-In Date");

            }
            viewPager.setCurrentItem(fragmentId);
        }
        tabLayout.setupWithViewPager(viewPager);

//        setResultForCheckinCheckOut(checkIn,checkOut,rooms,guests);

//        switch (viewPager.getCurrentItem()){
//            case 0:
//                actionbar.setTitle(R.string.check_in_date);
//                break;
//            case 1:
//                actionbar.setTitle("Select Check-Out Date");
//                break;
//        }

//        Fragment test = getSupportFragmentManager().findFragmentByTag("Room & Guest");
//        if (test != null && test.isVisible()) {
//            //DO STUFF
//            getSupportActionBar().setTitle("hello");
//        }
//        else {
//            //Whatever
//            getSupportActionBar().setTitle("hi");
//        }

    }

    public void setResultForCheckinCheckOut(String checkIn, String checkOut, String rooms, String guests) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("checkIn",checkIn);
        resultIntent.putExtra("checkOut",checkOut);
//        resultIntent.putExtra("rooms",rooms);
//        resultIntent.putExtra("guests",guests);
        setResult(Activity.RESULT_OK,resultIntent);

        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onFragmentInteractionC(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentChange(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.apply:
                onSupportNavigateUp();
                break;
        }
    }
}
