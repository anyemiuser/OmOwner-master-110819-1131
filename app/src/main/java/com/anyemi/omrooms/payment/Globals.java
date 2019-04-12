package com.anyemi.omrooms.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.UI.PaymentTransactionStatusActivity;
import com.google.gson.Gson;


/**
 * Created by Anuprog on 10/19/2016.
 */

public class Globals {
    public static final String SPF_NAME = "NEXER";

    public static void showToast(Context applicationContext, String msg) {
        try {
            Toast toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, 12);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static PaymentRequestModel getPaymentRequestModes(Bundle parametros) {

        try {
            String data = parametros.getString(Constants.PAYMENT_REQUEST_MODEL);
            PaymentRequestModel paymentRequestModel = new Gson().fromJson(data, PaymentRequestModel.class);
            return paymentRequestModel;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void ProceedNextScreen(final Context context, final PaymentRequestModel paymentRequestModel) {

        Intent intent = new Intent(context, PaymentTransactionStatusActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
        context.startActivity(intent);
    }
}
