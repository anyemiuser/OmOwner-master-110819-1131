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
import android.widget.Toast;

import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.CalenderActivity;
import com.anyemi.omrooms.Utils.ConverterUtil;

import java.util.Calendar;
import java.util.zip.Inflater;

public class CalenderFragmentCheckOut extends Fragment {
    private OnFragmentInteractionListenerC mListener;
    private OnFragmentChangeListner changeListner;

    private CalendarView calendarView;

    private View rootView;

    public CalenderFragmentCheckOut() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Check out","on resume");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.calendar_fragment_check_out, container, false);
        calendarView = rootView.findViewById(R.id.calendar_view_checkout);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCalenderDate();


//        calendarView.setMinDate(date);
//        calendarView.setDate(date);
        Log.e("check out ra","yes");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = ""+i2+"/"+(i1+1)+"/"+i;
                Log.e("date",""+i+" "+i1+" "+i2+" ");
//                CalenderActivity calenderActivity =new CalenderActivity();
//                CalenderActivity.viewPager.setCurrentItem(1);
                if(ConverterUtil.noOfDays(CalenderActivity.checkIn,date) > 0){
                    changeListner.onFragmentChange(2);
                    CalenderActivity.checkOut=date;
                }else {
                    Toast.makeText(getActivity(), "Check-In Date is "+CalenderActivity.checkIn, Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void setCalenderDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR,+1);

//        calendarView = view.findViewById(R.id.calendar_view_checkout);

        long date = ConverterUtil.ConvertDateToSetOnCalender(CalenderActivity.checkOut);
        calendarView.setMinDate(calendar.getTime().getTime());
        calendarView.setDate(date);



        Log.e("ccc",""+date);
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
                setCalenderDate();
            }
            Log.e("Check out start",""+CalenderActivity.checkOut);
            long date = ConverterUtil.ConvertDateToSetOnCalender(CalenderActivity.checkOut);

//            calendarView = rootView.findViewById(R.id.calendarView);
////
//            calendarView.setMinDate(date);
//            calendarView.setDate(date);
//            Log.e("ccc",""+date);
        }else{
            // fragment is not visible
        }
    }
}