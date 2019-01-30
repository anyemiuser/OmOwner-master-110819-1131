package com.anyemi.omrooms.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.CalenderActivity;

import java.util.Calendar;

public class CalenderFragmentCheckOut extends Fragment {
    private OnFragmentInteractionListenerC mListener;
    private OnFragmentChangeListner changeListner;

    private CalendarView calendarView;

    public CalenderFragmentCheckOut() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.calender_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();

//        long inactiveDate = calendar.getTime().getTime();
        calendar.add(Calendar.DAY_OF_YEAR,+1);

        long date = calendar.getTime().getTime();

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setMinDate(date);
        calendarView.setDate(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = ""+i2+"/"+i1+1+"/"+i;
                Log.e("date",""+i+" "+i1+" "+i2+" ");
//                CalenderActivity calenderActivity =new CalenderActivity();
//                CalenderActivity.viewPager.setCurrentItem(1);
                changeListner.onFragmentChange(2);
                CalenderActivity.checkOut=date;


            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListenerC) context;
            changeListner = (OnFragmentChangeListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListenerC {
        public void onFragmentInteractionC(String title);
    }

    public interface OnFragmentChangeListner{
        public void onFragmentChange(int position);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {

            if (mListener != null ) {
                mListener.onFragmentInteractionC("Select Check-Out Date");
            }
        }else{
            // fragment is not visible
        }
    }
}