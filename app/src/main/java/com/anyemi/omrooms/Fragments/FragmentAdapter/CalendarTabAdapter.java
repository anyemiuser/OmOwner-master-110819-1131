package com.anyemi.omrooms.Fragments.FragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anyemi.omrooms.Fragments.CalenderFragmentCheckIn;
import com.anyemi.omrooms.Fragments.CalenderFragmentCheckOut;
import com.anyemi.omrooms.Fragments.HomeFragment;
import com.anyemi.omrooms.Fragments.RoomGuestFragment;

public class CalendarTabAdapter extends FragmentStatePagerAdapter {
    int noOfTab;
    public CalendarTabAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTab = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CalenderFragmentCheckIn checkIn = new CalenderFragmentCheckIn();
                return checkIn;
            case 1:
                CalenderFragmentCheckOut checkOut = new CalenderFragmentCheckOut();
                return checkOut;
            case 2:
                RoomGuestFragment guestFragment = new RoomGuestFragment();
                return guestFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTab;
    }
}
