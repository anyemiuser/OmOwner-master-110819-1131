package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Fragments.FragmentAdapter.BookingHistoryAdapter;
import com.anyemi.omrooms.R;

public class BookingFragment extends Fragment {
    //Toolbar toolbarBookings;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return inflater.inflate(R.layout.fragment_booking, container, false);
//        return inflater.inflate(R.layout.fragment_home,null);
//        View view = inflater.inflate(R.layout.fragment_booking, container, false);
//        toolbarBookings = view.findViewById(R.id.toolbar_booking);
//        toolbarBookings.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        //child fragment inside fragment
        BookingHistoryAdapter adapter = new BookingHistoryAdapter(getChildFragmentManager());
        adapter.addFragment(new BookingHistoryFragment(), "Up Coming");
        adapter.addFragment(new BookingCancelledFragment(), "Cancelled");
        adapter.addFragment(new BookingCompletedFragment(), "Completed");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
