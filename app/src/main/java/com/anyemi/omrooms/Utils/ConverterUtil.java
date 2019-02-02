package com.anyemi.omrooms.Utils;

import android.util.Log;

import com.anyemi.omrooms.UI.CalenderActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ConverterUtil {

    public static long ConvertDateToSetOnCalender(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long dateTosetOnCalender = 0;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
//            calendar.add(Calendar.DAY_OF_YEAR, +1);
//            Date newDate = calendar.getTime();
//            String newDateF = dateFormat.format(newDate);
            dateTosetOnCalender = calendar.getTime().getTime();
            Log.e("Convertor",""+dateTosetOnCalender+" p day: "+date);

//            CalenderActivity.checkOut=newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTosetOnCalender;
    }

    public static String setTodaysDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        date= dateFormat.format(date1);
        return date;

    }

    public static String setDefaultCheckOutDateToNextDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nextDay = null;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            Date newDate = calendar.getTime();
            nextDay = dateFormat.format(newDate);
            Log.e("next day a",""+nextDay+" p day a: "+date);

//            return newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;

    }

    public static boolean checkCurrentDateIsLessThenSaved(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        try {
            Date savedDate = dateFormat.parse(date);
            calendar.setTime(savedDate);
            Date sDate = calendar.getTime();

            long diff = sDate.getTime() - currentDate.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.e("diff",""+days);
            if(days>=0){
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static int noOfDays(String checkInDate, String checkOutDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        long days = 0;
        try {
            Date date1 = dateFormat.parse(checkInDate);
            calendar.setTime(date1);
            Date inDate = calendar.getTime();

            Date date2 = dateFormat.parse(checkOutDate);
            calendar.setTime(date2);
            Date outDate = calendar.getTime();

            long diff = outDate.getTime() - inDate.getTime();
            days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

            return (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) days;
    }
}
