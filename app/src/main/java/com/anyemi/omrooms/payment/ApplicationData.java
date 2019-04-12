package com.anyemi.omrooms.payment;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.anyemi.omrooms.Model.ServicesResponseModel;

import java.util.ArrayList;

public class ApplicationData extends Application {
    public final static boolean IS_DEBUGGING_ON = false;
    public Typeface font = null;
    public Typeface fontbold = null;

    public static SharedPreferences appSharedPreferences;

    String PlanName;

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

    }

    PaymentRequestModel paymentRequestModel;
    ServicesResponseModel servicesResponseModel;

    public ServicesResponseModel getServicesResponseModel() {
        return servicesResponseModel;
    }

    public void setServicesResponseModel(ServicesResponseModel servicesResponseModel) {
        this.servicesResponseModel = servicesResponseModel;
    }





}