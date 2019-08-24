package org.sairaa.omowner.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.sairaa.omowner.payment.instamojo.model.InstamojoPaymentModel;

import java.util.ArrayList;
import java.util.List;


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



    public static String getInstaAmount(
            Context activity,
            String billAmount,
            InstamojoPaymentModel InstamojoPaymentModel,
            String type) {

        try {

            List<InstamojoPaymentModel.InstServiceTaxBean> taxArrayBean = new ArrayList<>();
            taxArrayBean.addAll((List<InstamojoPaymentModel.InstServiceTaxBean>) InstamojoPaymentModel.getInst_service_tax());



            String GST_CARD = "";
            String SERVICE_TAX = "";
            String ANYEMI_CHARGES = "";
            String TOTAL_AMOUNT_WITH_ALL_CHARGES = "";


            double TERM_BILL_AMOUNT = 0; //with Arrears etc

            String FIN_ID = SharedPreferenceUtil.getFIN_ID(activity);
            //String FIN_ID = "53";


            for (int i = 0; i < taxArrayBean.size(); i++) {
                Double upper_limit = Double.parseDouble(taxArrayBean.get(i).getTo());
                Double lower_limit = Double.parseDouble(taxArrayBean.get(i).getFrom());
                TERM_BILL_AMOUNT = Double.parseDouble(billAmount);


                if (type.equals(taxArrayBean.get(i).getPayment_type()) && upper_limit >
                        TERM_BILL_AMOUNT && lower_limit < TERM_BILL_AMOUNT) {

                    ANYEMI_CHARGES = taxArrayBean.get(i).getExtra_tax();
                    GST_CARD = taxArrayBean.get(i).getGst_perc();
                    SERVICE_TAX = taxArrayBean.get(i).getExtra_tax_perc();




                    Double card_charges = Double.parseDouble(SERVICE_TAX);
                    Double gst_on_card_charges = Double.parseDouble(GST_CARD);

                    card_charges = (card_charges * TERM_BILL_AMOUNT) / 100;
                    gst_on_card_charges = (card_charges * gst_on_card_charges) / 100;

                    Double final_amount = 0.0;

                    if (gst_on_card_charges > 0) {
                        final_amount = card_charges + TERM_BILL_AMOUNT + gst_on_card_charges;
                    } else {
                        final_amount = Double.valueOf(TERM_BILL_AMOUNT);
                    }

                    TOTAL_AMOUNT_WITH_ALL_CHARGES = String.valueOf(final_amount + Double.parseDouble(ANYEMI_CHARGES));
                    break;
                }
            }






            return TOTAL_AMOUNT_WITH_ALL_CHARGES;


        } catch (Exception e) {
            Globals.showToast(activity, "Invalid Data");
            return null;


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

//        Intent intent = new Intent(context, PaymentTransactionStatusActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//        context.startActivity(intent);
    }
}
