package com.anyemi.omrooms.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anyemi.omrooms.R;

import java.util.Set;


public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_prferance), Context.MODE_PRIVATE);

    }

    public void writeCheckInDate(String setCheck){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.check_in_out_preference),setCheck);
        editor.apply();
    }

    public String readCheckInDate(){
        String checkIn;
        checkIn = sharedPreferences.getString(context.getResources().getString(R.string.check_in_out_preference),null);
        return checkIn;
    }

    public void writeCheckOutDate(String setCheck){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.check_out_preference),setCheck);
        editor.apply();
    }

    public String readCheckOutDate(){
        String checkIn;
        checkIn = sharedPreferences.getString(context.getResources().getString(R.string.check_out_preference),null);
        return checkIn;
    }

    public void writeNoOfRooms(int rooms){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getResources().getString(R.string.no_of_rooms_preference),rooms);
        editor.apply();
    }

    public int readNoOfRooms(){
        int rooms;
        rooms = sharedPreferences.getInt(context.getResources().getString(R.string.no_of_rooms_preference),1);
        return rooms;
    }

    public void writeNoOfGuests(int guests){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getResources().getString(R.string.no_of_guests_preference),guests);
        editor.apply();
    }

    public int readNoOfGuests(){
        int guests;
        guests = sharedPreferences.getInt(context.getResources().getString(R.string.no_of_guests_preference),1);
        return guests;
    }


    public void writePhoneNo(String phoneNo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.phone_no_preference), phoneNo);
        Log.i("SharedPreferanceWrite: ",""+phoneNo);
        editor.commit();
    }

    public String readPhoneNo(){
        String phoneNo;
        phoneNo = sharedPreferences.getString(context.getResources().getString(R.string.phone_no_preference),"no");
        Log.i("SharedPreferanceRead: ",""+phoneNo);
        return phoneNo;
    }

    public void writeName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.name_preference), name);
        Log.i("SharedPreferanceWrite: ",""+name);
        editor.commit();
    }

    public String readName(){
        String name;
        name = sharedPreferences.getString(context.getResources().getString(R.string.name_preference),"no");
        Log.i("SharedPreferanceRead: ",""+name);
        return name;
    }
    public void writeCityName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.city_name_preference), name);
        Log.i("SharedPreferanceWrite: ",""+name);
        editor.apply();
    }

    public String readCityName() {
        String name;
        name = sharedPreferences.getString(context.getResources().getString(R.string.city_name_preference),null);
        Log.i("SharedPreferanceRead: ",""+name);
        return name;
    }

    public void writeStateName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.state_name_preference), name);
        Log.i("SharedPreferanceWrite: ",""+name);
        editor.apply();
    }

    public String readStateName() {
        String name;
        name = sharedPreferences.getString(context.getResources().getString(R.string.state_name_preference),null);
        Log.i("SharedPreferanceRead: ",""+name);
        return name;
    }

    public void writeDistrictName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.district_name_preference), name);
        Log.i("SharedPreferanceWrite: ",""+name);
        editor.apply();
    }

    public String readDistrictName() {
        String name;
        name = sharedPreferences.getString(context.getResources().getString(R.string.district_name_preference),null);
        Log.i("SharedPreferanceRead: ",""+name);
        return name;
    }
}
