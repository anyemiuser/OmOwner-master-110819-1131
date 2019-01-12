package com.anyemi.omrooms.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anyemi.omrooms.R;


public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_prferance), Context.MODE_PRIVATE);

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

}
