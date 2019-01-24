package com.anyemi.omrooms.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.anyemi.omrooms.Fragments.CalenderFragmentCheckIn;
import com.anyemi.omrooms.Fragments.CalenderFragmentCheckOut;
import com.anyemi.omrooms.Fragments.FragmentAdapter.CalenderFragmentAdapter;
import com.anyemi.omrooms.Fragments.RoomGuestFragment;
import com.anyemi.omrooms.R;

public class CalenderActivity extends AppCompatActivity implements RoomGuestFragment.OnFragmentInteractionListener,
        CalenderFragmentCheckIn.OnFragmentInteractionListenerC,
        CalenderFragmentCheckOut.OnFragmentInteractionListenerC {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        int fragmentId = getIntent().getIntExtra("check",-1);
        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setTitle(R.string.checkin);
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        //child fragment inside fragment
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

            }
            viewPager.setCurrentItem(fragmentId);
        }
        tabLayout.setupWithViewPager(viewPager);

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

    @Override
    public boolean onSupportNavigateUp() {
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
}
