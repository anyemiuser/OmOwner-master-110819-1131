package com.anyemi.omrooms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.anyemi.omrooms.Fragments.BookingFragment;
import com.anyemi.omrooms.Fragments.HomeFragment;
import com.anyemi.omrooms.Fragments.ProfileFragment;
import com.anyemi.omrooms.Fragments.SavedFragment;
import com.anyemi.omrooms.Helper.RGuest;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity implements
        SavedFragment.OnFragmentBackListner,
        BookingFragment.OnBookingFragmentBackListner{

    BottomNavigationView bottomNavigationView;
    RGuest rGuest;
    private SharedPreferenceConfig sharedPreferenceConfig;

    boolean doubleBackToExitPressedOnce = false;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rGuest = new RGuest();
        setContentView(R.layout.activity_main);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        setDefaultDateAndRooms();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.action_saved:
                        fragment = new SavedFragment();
                        break;

                    case R.id.action_bookings:
                        fragment = new BookingFragment();
                        break;
                    case R.id.action_account:
                        fragment = new ProfileFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private void setDefaultDateAndRooms() {
        boolean isCurrentDate = false;
        if(sharedPreferenceConfig.readCheckInDate() == null){
            String checkIn = ConverterUtil.setTodaysDate();
            Log.e("check in in calendar",""+checkIn);
            String  checkOut = ConverterUtil.setDefaultCheckOutDateToNextDay(checkIn);
            sharedPreferenceConfig.writeCheckInDate(checkIn);
            sharedPreferenceConfig.writeCheckOutDate(checkOut);
            Log.e("check out in calendar",""+checkOut);
            sharedPreferenceConfig.writeNoOfRooms(1);
            sharedPreferenceConfig.writeNoOfGuests(2);
        }else {
            isCurrentDate = ConverterUtil.checkCurrentDateIsLessThenSaved(sharedPreferenceConfig.readCheckInDate());
            if(isCurrentDate){
                //no need to change
            }else {
                String checkIn = ConverterUtil.setTodaysDate();
                Log.e("check in in calendar",""+checkIn);
                String  checkOut = ConverterUtil.setDefaultCheckOutDateToNextDay(checkIn);
                sharedPreferenceConfig.writeCheckInDate(checkIn);
                sharedPreferenceConfig.writeCheckOutDate(checkOut);
                Log.e("check out in calendar",""+checkOut);
                sharedPreferenceConfig.writeNoOfRooms(1);
                sharedPreferenceConfig.writeNoOfGuests(2);
            }
        }

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
//                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onFragmentChange() {
//        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

//    @Override
//    public void onBackPressed() {
//        int stackCount = getSupportFragmentManager().getBackStackEntryCount();
//        if (stackCount == 1) {
//            super.onBackPressed();
//        } else  {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.fragment_container, new HomeFragment());
//            transaction.commit();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please Click BACK again to Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
