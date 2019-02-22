package com.anyemi.omrooms.payment;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




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
}
