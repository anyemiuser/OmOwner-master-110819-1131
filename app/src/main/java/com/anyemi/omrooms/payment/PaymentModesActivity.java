package com.anyemi.omrooms.payment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.anyemi.omrooms.payment.bgtask.BackgroundTask;
import com.anyemi.omrooms.payment.bgtask.BackgroundThread;
import com.anyemi.omrooms.payment.connection.HomeServices;
import com.google.gson.Gson;
import com.anyemi.omrooms.R;

/**
 * Created by SuryaTejaChalla on 24-01-2018.
 */

public class PaymentModesActivity extends AppCompatActivity implements View.OnClickListener {



    //Action Bar
    TextView aTitle, notification_count;
    RelativeLayout rl_new_mails;
    ImageView iv_add_new;
    Toolbar toolbar;

    //Payment Details
    String paymentData;
    PaymentRequestModel paymentRequestModel;

    //View

    LinearLayout ll_cash, ll_cheque, ll_others, ll_credit_debit, ll_aadhar_pay, ll_scan_pay,
            ll_bhim, ll_internet_banking, ll_wallet;
    TextView tv_amount;
    EditText et_phone_num;
    Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_modes2);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(PaymentModesActivity.this, R.color.colorPrimaryDark));
        }
        try {
            Bundle parametros = getIntent().getExtras();
            paymentData = parametros.getString(Constants.PAYMENT_REQUEST_MODEL);
            Gson gson = new Gson();
            paymentRequestModel = gson.fromJson(paymentData, PaymentRequestModel.class);



        } catch (Exception e) {
            e.printStackTrace();
        }

        createActionBar();
        initView();
        getWalletBalance(SharedPreferenceUtil.getUserId(getApplicationContext()));


    }

    private void getWalletBalance(final String userId) {

        new BackgroundTask(PaymentModesActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return HomeServices.getWallectBalance(PaymentModesActivity.this, userId);
            }

            public void taskCompleted(Object data) {
                if (data != null || data.equals("")) {

                       WalletMOdel walletMOdel= new WalletMOdel();
                       walletMOdel= new Gson().fromJson(data.toString(),WalletMOdel.class);

                       if(walletMOdel.getStatus()!=null && walletMOdel.getStatus().equals("Success")){
                           SharedPreferenceUtil.setWalletBalance(getApplicationContext(),walletMOdel.getWallet_amount());
                       }else{
                           SharedPreferenceUtil.setWalletBalance(getApplicationContext(),"");
                           Globals.showToast(getApplicationContext(),walletMOdel.getMsg());
                       }


                }
            }
        }, getString(R.string.loading_txt)).execute();
    }

    private void initView() {

        tv_amount = (TextView) findViewById(R.id.tv_amount);

        ll_cash = (LinearLayout) findViewById(R.id.ll_cash);
        ll_cheque = (LinearLayout) findViewById(R.id.ll_cheque);
        ll_credit_debit = (LinearLayout) findViewById(R.id.ll_credit_debit);
        ll_aadhar_pay = (LinearLayout) findViewById(R.id.ll_aadhar_pay);
        ll_scan_pay = (LinearLayout) findViewById(R.id.ll_scan_pay);
        ll_bhim = (LinearLayout) findViewById(R.id.ll_bhim);
        ll_internet_banking = (LinearLayout) findViewById(R.id.ll_internet_banking);
        ll_wallet = (LinearLayout) findViewById(R.id.ll_wallet);
        ll_others = (LinearLayout) findViewById(R.id.ll_others);

        String resultStr = String.valueOf(paymentRequestModel.getTotal_amount());
        resultStr = Utils.parseAmount(resultStr);

        tv_amount.setText("Rs." + resultStr + " /-");

        String l_type = SharedPreferenceUtil.getGroupId(getApplicationContext());

        if (l_type.equals(Constants.LOGIN_TYPE_CUSTOMER)) {
            ll_cash.setVisibility(View.GONE);
            ll_cheque.setVisibility(View.GONE);
            ll_credit_debit.setVisibility(View.GONE);
            ll_aadhar_pay.setVisibility(View.GONE);
            ll_scan_pay.setVisibility(View.GONE);
            ll_internet_banking.setVisibility(View.GONE);
        } else {
            ll_cash.setVisibility(View.VISIBLE);
            ll_cheque.setVisibility(View.VISIBLE);
            ll_credit_debit.setVisibility(View.VISIBLE);
            ll_aadhar_pay.setVisibility(View.VISIBLE);

            if (SharedPreferenceUtil.getPrintHeader(getApplicationContext()).equals(Constants.PRINT_HEADER_APEPDCL)) {
                ll_cash.setVisibility(View.VISIBLE);
                ll_cheque.setVisibility(View.GONE);
//            ll_credit_debit.setVisibility(View.GONE);
//            ll_aadhar_pay.setVisibility(View.GONE);
            } else {
                ll_cash.setVisibility(View.VISIBLE);
                ll_cheque.setVisibility(View.VISIBLE);
//            ll_credit_debit.setVisibility(View.VISIBLE);
//            ll_aadhar_pay.setVisibility(View.VISIBLE);
            }


           if(paymentRequestModel.getRemarks().equals(Constants.PAYMENT_REMARKS_ANNA)){
               ll_cash.setVisibility(View.VISIBLE);
               ll_others.setVisibility(View.VISIBLE);
               ll_cheque.setVisibility(View.GONE);
               ll_credit_debit.setVisibility(View.GONE);
               ll_aadhar_pay.setVisibility(View.GONE);
               ll_scan_pay.setVisibility(View.GONE);
               ll_internet_banking.setVisibility(View.GONE);
               ll_wallet.setVisibility(View.GONE);

           }


        }




        ll_cash.setOnClickListener(this);
        ll_cheque.setOnClickListener(this);
        ll_credit_debit.setOnClickListener(this);
        ll_aadhar_pay.setOnClickListener(this);
        ll_scan_pay.setOnClickListener(this);
        ll_bhim.setOnClickListener(this);
        ll_internet_banking.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        ll_others.setOnClickListener(this);

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
        aTitle.setTextColor(getResources().getColor(R.color.white));
        aTitle.setText("Choose payment Method");
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
                et_phone_num.setText(message);
                dialog.dismiss();

            }
        }
    };


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_others:
               // Globals.showToast(getApplicationContext(),"SBI UPI is under process.We will update once process done");
                getSelectedPayment(Constants.PAYMENT_MODE_PAYTM_SBI_UPI);
                break;
            case R.id.ll_cash:
             //   getSelectedPayment(Constants.PAYMENT_MODE_CASH);
                getSelectedPayment(Constants.PAYMENT_MODE_ANYEMI_WALLET);
                break;
            case R.id.ll_cheque:
                getSelectedPayment(Constants.PAYMENT_MODE_CHEQUE);
                break;
            case R.id.ll_credit_debit:
                getSelectedPayment(Constants.PAYMENT_MODE_CREDIT_CARD);
                break;
            case R.id.ll_scan_pay:
                getSelectedPayment(Constants.PAYMENT_MODE_PAYTM_QR);
                break;
            case R.id.ll_bhim:
                getSelectedPayment(Constants.PAYMENT_MODE_BHIM);
                break;
            case R.id.ll_internet_banking:
                getSelectedPayment(Constants.PAYMENT_MODE_NET_BANKING);
                break;
            case R.id.ll_aadhar_pay:
                getSelectedPayment(Constants.PAYMENT_MODE_AADHAR);
                break;
            case R.id.ll_wallet:
                getSelectedPayment(Constants.PAYMENT_MODE_PAYTM_PG);
                break;
        }
    }

    public void getSelectedPayment(String payment_mode) {

        Intent paymentIntent;

        if (payment_mode.equals(Constants.PAYMENT_MODE_PAYTM_SBI_UPI)) {
//            paymentRequestModel.setPayment_type(Constants.PAYMENT_MODE_PAYTM_SBI_UPI);
//            paymentIntent = new Intent(getApplicationContext(), SbiPayPaymentActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_CASH)) {
//            paymentRequestModel.setPayment_type(Constants.PAYMENT_MODE_CASH);
//            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        }else if (payment_mode.equals(Constants.PAYMENT_MODE_ANYEMI_WALLET)) {
//            paymentRequestModel.setPayment_type(Constants.PAYMENT_MODE_ANYEMI_WALLET);
//            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_CHEQUE)) {
//            paymentIntent = new Intent(getApplicationContext(), CheckActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_CREDIT_CARD)) {
//            paymentIntent = new Intent(getApplicationContext(), CreditAndDebitCardActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);

            //initMSwipe();


        } else if (payment_mode.equals(Constants.PAYMENT_MODE_PAYTM_QR)) {
//            paymentIntent = new Intent(getApplicationContext(), ScanAndPayActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_BHIM)) {
//            paymentIntent = new Intent(getApplicationContext(), BhimActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_NET_BANKING)) {
            paymentRequestModel.setPayment_type(Constants.PAYMENT_MODE_NET_BANKING);
//            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_AADHAR)) {
//            paymentRequestModel.setPayment_type(Constants.PAYMENT_MODE_AADHAR);
//            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_PAYTM_PG)) {
//            paymentIntent = new Intent(getApplicationContext(), PaymentWalletActivity.class);
//            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
//            startActivity(paymentIntent);


         //   Globals.showToast(getApplicationContext(),"We will Update soon");
        }
    }

/*

    private void submitPayment() {

        new BackgroundTask(PaymentModesActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return HomeServices.submitPayment(PaymentModesActivity.this, paymentRequestModel);
            }

            public void taskCompleted(Object data) {
                if (data != null || data.equals("")) {
                    if (data.toString().contains("SUCCESS")) {
                        Globals.showToast(getApplicationContext(), "Payment Success");
                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("FRAGMENT","COLLECTION");
                        startActivity(intent);
                    }
                }
            }
        }, getString(R.string.loading_txt)).execute();
    }

*/

}