package com.anyemi.omrooms.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.anyemi.omrooms.Fragments.BookingHistoryAdapter;
import com.anyemi.omrooms.Fragments.BookingHistoryFragment;
import com.anyemi.omrooms.Fragments.CalenderFragment;
import com.anyemi.omrooms.Fragments.CalenderFragmentAdapter;
import com.anyemi.omrooms.R;

public class CalenderActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(R.string.checkin);
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        //child fragment inside fragment
        CalenderFragmentAdapter adapter = new CalenderFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CalenderFragment(),"Check In");
        adapter.addFragment(new CalenderFragment(),"Check Out");
        adapter.addFragment(new CalenderFragment(),"Room");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
