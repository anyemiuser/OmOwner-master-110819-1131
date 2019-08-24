package org.sairaa.omowner.payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.sairaa.omowner.R;
import org.sairaa.omowner.payment.bgtask.BackgroundTask;
import org.sairaa.omowner.payment.bgtask.BackgroundThread;
import org.sairaa.omowner.payment.connection.HomeServices;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by SuryaTejaChalla on 07-02-2018.
 */

public class CompleateTransactionActivity extends AppCompatActivity implements TextWatcher {

    //ACTION BAR UI COMPONENTS

    TextView aTitle, notification_count;
    RelativeLayout rl_new_mails;
    ImageView iv_add_new;
    Toolbar toolbar;

    // VARIBLES

    String data;
    PaymentRequestModel paymentRequestModel;
    TextView tv_amount;

    //UI COMPONENTS

    Button proceed;
    Dialog dialog;

    EditText et_trns_id, et_phone_num, et_aadhar;
    TextInputLayout til_trns_id, til_phone_id, til_aadhar;


    //Payment Gate Way

    String orderId, payment_id;
    String hash;
    Gson gson;
    Dialog infoDialog;

    //   boolean Security=false;

//    MSWIPE




    boolean isPreAuth = false;
    boolean isSaleWithCash = false;
    private String CARDSALE_DIALOG_MSG = "MswipeWisepad Cardsale";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_transaction);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(CompleateTransactionActivity.this, R.color.colorPrimaryDark));
        }


        try {
            Bundle parametros = getIntent().getExtras();
            data = parametros.getString(Constants.PAYMENT_REQUEST_MODEL);
            gson = new Gson();
            paymentRequestModel = gson.fromJson(data, PaymentRequestModel.class);

            // Payment type Credit card or Debit Card

            if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_DEBIT_CARD) ||
                    paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_CREDIT_CARD)) {


                isPreAuth = getIntent().getBooleanExtra("isPreAuth", false);
                isSaleWithCash = getIntent().getBooleanExtra("isSaleWithCash", false);


                if (isPreAuth)
                    CARDSALE_DIALOG_MSG = " Pre Auth";
                else if (isSaleWithCash)
                    CARDSALE_DIALOG_MSG = " Sale Cash";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        createActionBar();
        initView2();
        if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_PAYTM_PG)) {
            til_trns_id.setVisibility(View.GONE);

        }

        til_trns_id.setVisibility(View.GONE);
    }


    private void createActionBar() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar_with_home_button, null);

        aTitle = (TextView) mCustomView.findViewById(R.id.title_text);
        rl_new_mails = (RelativeLayout) mCustomView.findViewById(R.id.rl_new_mails);
        notification_count = (TextView) mCustomView.findViewById(R.id.text_count);
        iv_add_new = (ImageView) mCustomView.findViewById(R.id.iv_add_new);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        aTitle.setTextColor(getResources().getColor(R.color.colorWhite));
        aTitle.setText("Complete payment ");

    }

    private void initOrderId() {
        Random r = new Random(System.currentTimeMillis());
        orderId = "ORDER" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);

       // initPaymentRequest();
        // generateHashKey();
    }







    private String paymentRequestModel() {
        try {

            JSONObject jsonObject = new JSONObject();

        /*    jsonObject.put("MID", "ANYEMI64501677609833");
            jsonObject.put("ORDER_ID", orderId);
            jsonObject.put("CUST_ID", paymentRequestModel.getAssessment_id().replace(" ", ""));
            jsonObject.put("INDUSTRY_TYPE_ID", "Retail92");
            jsonObject.put("CHANNEL_ID", "WAP");
            jsonObject.put("TXN_AMOUNT", paymentRequestModel.getTotal_amount());
            // jsonObject.put("TXN_AMOUNT", "1");
            jsonObject.put("WEBSITE", "ANYEMIWAP");
            jsonObject.put("EMAIL", "");
            jsonObject.put("MOBILE_NO", et_phone_num.getText().toString());
            jsonObject.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + orderId);
*/

            jsonObject.put("MID", "ANYEMI14182027236332");
            //jsonObject.put("MID", "ANYEMI64501677609833");  //Updated on 08/28/2018 as per sakshi instruction
            jsonObject.put("ORDER_ID", orderId);
            jsonObject.put("CUST_ID", paymentRequestModel.getAssessment_id().replace(" ", ""));
            jsonObject.put("INDUSTRY_TYPE_ID", "Retail");
            jsonObject.put("CHANNEL_ID", "WAP");
            jsonObject.put("TXN_AMOUNT", paymentRequestModel.getTotal_amount());
            // jsonObject.put("TXN_AMOUNT", "1");
            jsonObject.put("WEBSITE", "APP_STAGING");
            jsonObject.put("EMAIL", "");
            jsonObject.put("MOBILE_NO", et_phone_num.getText().toString());
            jsonObject.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");


            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }







    private void initView2() {

        tv_amount = (TextView) findViewById(R.id.tv_amount);
        proceed = (Button) findViewById(R.id.btn_pay);
        et_trns_id = (EditText) findViewById(R.id.et_trns_id);
        et_phone_num = (EditText) findViewById(R.id.et_phone_num);
        et_aadhar = (EditText) findViewById(R.id.et_aadhar);
        til_trns_id = (TextInputLayout) findViewById(R.id.til_trns_id);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        et_trns_id.addTextChangedListener(this);
        et_aadhar.addTextChangedListener(this);
        et_phone_num.addTextChangedListener(this);
        til_phone_id = (TextInputLayout) findViewById(R.id.til_phone_id);
        til_aadhar = (TextInputLayout) findViewById(R.id.til_aadhar);
        proceed.setText("Proceed Payment");

        if (paymentRequestModel.getTrsno() != null && !paymentRequestModel.getTrsno().equals("")) {
            et_trns_id.setText(paymentRequestModel.getTrsno());
            et_trns_id.setEnabled(false);
        }

        String resultStr = Utils.parseAmount(String.valueOf(paymentRequestModel.getTotal_amount()));
        tv_amount.setText("Rs." + resultStr + " /-");

        if (paymentRequestModel.getMobile_number() != null) {
            if (paymentRequestModel.getMobile_number().length() < 10) {
                et_phone_num.setText("");
            } else {
                et_phone_num.setText(paymentRequestModel.getMobile_number());
            }
        }

        if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_ANYEMI_WALLET)) {
            til_trns_id.setVisibility(View.VISIBLE);
            til_trns_id.setHint("Wallet Balance");
            et_trns_id.setVisibility(View.VISIBLE);
            et_trns_id.setEnabled(false);
            et_trns_id.setText("Available Wallet Balance is Rs." + SharedPreferenceUtil.getWalletBalance(getApplicationContext()) + "/-");
        } else {
            til_trns_id.setVisibility(View.VISIBLE);
            et_trns_id.setVisibility(View.VISIBLE);
            et_trns_id.setEnabled(true);
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et_aadhar.getText().toString().equals("") && et_aadhar.getText().toString().length() != 12) {
                    Globals.showToast(getApplicationContext(), " Please enter valid Aadhar Number");
                }


                if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_CASH)) {


                    String sss =String.valueOf(paymentRequestModel.getTotal_amount());

//                    if (Integer.parseInt(SharedPreferenceUtil.getWalletBalance(getApplicationContext()))
//                            < Integer.parseInt(sss)) {
//                        Globals.showToast(getApplicationContext(), "Please Update your wallet Balance");
//                    } else
                    if (et_phone_num.getText().toString().equals("")) {
                        Globals.showToast(getApplicationContext(), " Please enter Mobile Number");
                    } else if (et_phone_num.getText().toString().length() == 10) {
                        paymentRequestModel.setMobile_number(et_phone_num.getText().toString());
                        submitPayment();
                    } else {
                        Globals.showToast(getApplicationContext(), "Please enter valid mobile number");
                    }
                } else if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_ANYEMI_WALLET)) {


                    String sss = paymentRequestModel.getTotal_amount();

                    if (Double.parseDouble(SharedPreferenceUtil.getWalletBalance(getApplicationContext()))
                            < Double.parseDouble(sss)) {
                        Globals.showToast(getApplicationContext(), "Please Update your wallet Balance");
                    } else if (et_phone_num.getText().toString().equals("")) {
                        Globals.showToast(getApplicationContext(), " Please enter Mobile Number");
                    } else if (et_phone_num.getText().toString().length() == 10) {
                        paymentRequestModel.setMobile_number(et_phone_num.getText().toString());
                        paymentRequestModel.setRr_number("");

                        double wallet_balance = Double.parseDouble(SharedPreferenceUtil.getWalletBalance(getApplicationContext()));
                        double ss = Double.parseDouble(sss);
                        double remarks = wallet_balance - ss;
                        if (paymentRequestModel.getRemarks().equals(Constants.PAYMENT_REMARKS_ANNA)) {

                        } else {
                            paymentRequestModel.setRemarks(remarks + "");
                        }
                        submitPayment();
                    } else {
                        Globals.showToast(getApplicationContext(), "Please enter valid mobile number");
                    }
                } else if (paymentRequestModel.getPayment_type().equals(Constants.PAYMENT_MODE_PAYTM_PG)) {

                    if (et_phone_num.getText().toString().equals("")) {
                        Globals.showToast(getApplicationContext(), " Please enter Mobile Number");
                    } else if (et_phone_num.getText().toString().length() == 10) {
                        paymentRequestModel.setMobile_number(et_phone_num.getText().toString());
                        initOrderId();
                    } else {
                        Globals.showToast(getApplicationContext(), "Please enter valid mobile number");
                    }


                } else if (performValidation()) {

//                    if (!paymentRequestModel.getPayment_type().equals(ConstantsData.PAYMENT_MODE_ANYEMI_WALLET)) {
//
//                    }

                    paymentRequestModel.setRr_number(et_trns_id.getText().toString());
                    submitPayment();
                }
            }
        });

    }


    private void submitPayment() {

        new BackgroundTask(CompleateTransactionActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return HomeServices.submitPayment(CompleateTransactionActivity.this, paymentRequestModel);
            }

            public void taskCompleted(Object data) {
                if (data != null || data.equals("")) {
                    if (data.toString().contains("SUCCESS")) {


//                        String amount=SharedPreferenceUtil.getWalletBalance(getApplicationContext());
//                       String bill= String.valueOf(paymentRequestModel.getTotal_amount());
//
//                       Double upDatedBalance=Double.parseDouble(amount)-Double.parseDouble(bill);
//
//                       SharedPreferenceUtil.setWalletBalance(getApplicationContext(),upDatedBalance+"");

                        Globals.showToast(getApplicationContext(), "Payment Success");
//                        Intent intent = new Intent(getApplicationContext(), CollectionsTabbedActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.putExtra("FRAGMENT", "COLLECTION");
//                        startActivity(intent);
                    }
                }
            }
        }, getString(R.string.loading_txt)).execute();
    }


    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("Wallet txn id:"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase("Wallet txn id:")) {
                final String message = intent.getStringExtra("id");
                // Globals.showToast(getApplicationContext(),message);
                et_phone_num.setText(message);
                dialog.dismiss();

            }
        }
    };


    private boolean performValidation() {
        boolean isValid = false;

        if (et_trns_id.getText().toString().equals("")) {
            til_trns_id.setError("Please enter Transaction id");
            et_trns_id.requestFocus();
        } else if (et_phone_num.getText().toString().equals("")) {
            til_phone_id.setError("Enter your Mobile Number");
            et_phone_num.requestFocus();
        } else if (et_phone_num.getText().toString().length() != 10) {
            til_phone_id.setError("Invalid Mobile Number");
            et_phone_num.requestFocus();
        } else {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (et_trns_id.hasFocus() && et_trns_id.getText().toString().length() > 0) {
            til_trns_id.setErrorEnabled(false);
            til_trns_id.setError(null);
        } else if (et_phone_num.hasFocus() && et_phone_num.getText().toString().length() > 0) {
            til_phone_id.setErrorEnabled(false);
            til_phone_id.setError(null);
        }

        if (et_trns_id.hasFocus() && et_trns_id.getText().toString().length() > 255) {
            Globals.showToast(getApplicationContext(), "More than 255 character are not allowed");
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}