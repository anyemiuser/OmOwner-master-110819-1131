package com.anyemi.omrooms.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyemi.omrooms.R;
import com.anyemi.omrooms.payment.PaymentRequestModel;
import com.google.gson.Gson;

public class PaymentTransactionStatusActivity extends AppCompatActivity {

    //ACTION BAR UI COMPONENTS

    TextView aTitle, notification_count;
    RelativeLayout rl_new_mails;
    ImageView iv_add_new;
    Toolbar toolbar;

    // VARIBLES

    String data;
    PaymentRequestModel paymentRequestModel;
    TextView tv_amount, tv_payment_to, tv_upi_id, tv_transaction_id,tv_trns_status;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(PaymentTransactionStatusActivity.this, R.color.colorPrimaryDark));
        }


        try {
            Bundle parametros = getIntent().getExtras();
            data = parametros.getString(Constants.PAYMENT_REQUEST_MODEL);
            Gson gson = new Gson();
            paymentRequestModel = gson.fromJson(data, PaymentRequestModel.class);


        } catch (Exception e) {
            e.printStackTrace();
        }


        createActionBar();
        initView();

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
        aTitle.setText("Complete payment ");

    }


    private void initView() {

        tv_amount = findViewById(R.id.tv_amount);
        tv_payment_to = findViewById(R.id.tv_payment_to);
        tv_upi_id = findViewById(R.id.tv_upi_id);
        tv_transaction_id = findViewById(R.id.tv_transaction_id);
        tv_trns_status = findViewById(R.id.tv_trns_status);
       ImageView iv_status = findViewById(R.id.iv_status);
        TextView tv_reason = findViewById(R.id.tv_reason);
        tv_reason.setVisibility(View.GONE);


        if (paymentRequestModel.getRemarks().contains("Collect Request rejected by customer")) {
            tv_trns_status.setText("Money Transfer Failed");
            tv_reason.setText(paymentRequestModel.getRemarks());
            tv_reason.setVisibility(View.VISIBLE);
            tv_trns_status.setTextColor(getResources().getColor(R.color.red_oval_color));
            iv_status.setImageResource(R.drawable.declined);
        }

        tv_amount.setText("Rs " + paymentRequestModel.getTotal_amount() + "/-");
        tv_upi_id.setText(paymentRequestModel.getUpi_id());
        tv_transaction_id.setText(paymentRequestModel.getTrsno());
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              //  intent.putExtra("FRAGMENT", "COLLECTION");
                startActivity(intent);
                break;

                default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}