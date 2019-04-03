package com.anyemi.omrooms.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Fragments.FragmentAdapter.BookingHistoryAdapter;
import com.anyemi.omrooms.R;

import java.util.Objects;

public class BookingFragment extends Fragment {
    //Toolbar toolbarBookings;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private OnBookingFragmentBackListner backListner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        return inflater.inflate(R.layout.fragment_booking, container, false);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
//        return inflater.inflate(R.layout.fragment_home,null);
//        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        Toolbar toolbarBookings = view.findViewById(R.id.toolbar_booking);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarBookings);
        ActionBar actionbar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Booking History");
        }
        toolbarBookings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backListner.onFragmentChange();
            }
        });
        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            backListner = (OnBookingFragmentBackListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnBookingFragmentBackListner{
        public void onFragmentChange();
    }
}
