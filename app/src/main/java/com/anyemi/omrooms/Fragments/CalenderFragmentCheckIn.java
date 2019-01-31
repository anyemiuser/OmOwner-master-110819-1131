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
import com.anyemi.omrooms.Utils.ConverterUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalenderFragmentCheckIn extends Fragment {
    private OnFragmentInteractionListenerC mListener;
    private OnFragmentChangeListner changeListner;


    private CalendarView calendarView;


    public CalenderFragmentCheckIn() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.calender_fragment_check_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();

        long inactiveDate = calendar.getTime().getTime();
//        calendar.add(Calendar.DAY_OF_YEAR,+1);

        long date = calendar.getTime().getTime();

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setMinDate(inactiveDate);
        calendarView.setDate(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = ""+i2+"/"+(i1+1)+"/"+i;
                Log.e("date",""+i+" "+i1+" "+i2+" "+date);
//                CalenderActivity calenderActivity =new CalenderActivity();
//                CalenderActivity.viewPager.setCurrentItem(1);

                //open next fragment


                CalenderActivity.checkIn=date;
                CalenderActivity.checkOut = ConverterUtil.setDefaultCheckOutDateToNextDay(CalenderActivity.checkIn);
//                setDefaultCheckOutDateToNextDay(date);
                changeListner.onFragmentChange(1);

            }
        });
    }

    private void setDefaultCheckOutDateToNextDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            Date newDate = calendar.getTime();
            String newDateF = dateFormat.format(newDate);
            Log.e("next day",""+newDateF+" p day: "+date);

            CalenderActivity.checkOut=newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                mListener.onFragmentInteractionC("Select Check-in Date");
            }
        }else{
            // fragment is not visible
        }
    }
}
